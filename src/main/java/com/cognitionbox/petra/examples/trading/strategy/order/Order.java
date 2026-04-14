package com.cognitionbox.petra.examples.trading.strategy.order;

import com.cognitionbox.petra.ast.interp.util.reactive.Updateable;
import com.cognitionbox.petra.ast.terms.*;
import com.cognitionbox.petra.examples.trading.strategy.data.DataSource;
import com.cognitionbox.petra.examples.trading.strategy.data.StatusType;

@Base
public final class Order implements Updateable {
    private float bid;
    private float ask;
    private float mid;
    private int currentHour;

    private boolean midAboveSma;
    private boolean midBelowSma;
    private boolean midEqualSma = true;

    private StatusType status = StatusType.CLOSED_EARLY;
    private float open;
    private float close;
    private float closedPnl;

    private float average;


    public boolean closedEarly() {
        return status==StatusType.CLOSED_EARLY;
    }


    public void enterBuy(){
        if (closedEarly()){
            open = ask;
            status=StatusType.BOUGHT;
            enterBuyLog();
            assert(bought());
        }
    }

    public void enterSell(){
        if (closedEarly()){
            open = bid;
            status=StatusType.SOLD;
            enterSellLog();
            assert(sold());
        }
    }

    public void closeEarly(){
        if (bought()){
            close = bid;
            closedPnl = closedPnl + (close - open);
            status=StatusType.CLOSED_EARLY;
            closeBuyLog();
            assert(closedEarly());
        } else if (sold()){
            close = ask;
            closedPnl = closedPnl + (open - close);
            status=StatusType.CLOSED_EARLY;
            closeSellLog();
            assert(closedEarly());
        }
    }

    public boolean bought() {return status==StatusType.BOUGHT;}

    public boolean sold() {return status==StatusType.SOLD;}

    private final int openHour = 8;
    private final int middayHour = 10;
    private final int closeHour = 14;
    private final int eodHour = 16;

    public boolean isOpen() {
        return currentHour >= openHour && currentHour < middayHour && openHour < middayHour && middayHour < closeHour && closeHour < eodHour;
    }

    public boolean isMidday() {
        return currentHour >= middayHour && currentHour < closeHour && openHour < middayHour && middayHour < closeHour && closeHour < eodHour;
    }

    public boolean isClose() {
        return currentHour >= closeHour && currentHour < eodHour && openHour < middayHour && middayHour < closeHour && closeHour < eodHour;
    }

    @Initial public boolean isNone() {
        return (currentHour < openHour || currentHour >= eodHour) && openHour < middayHour && middayHour < closeHour && closeHour < eodHour;
    }

    @Initial
    @NonDet public boolean midEqualSma() {
        return midEqualSma;
    }
    @NonDet public boolean midAboveSma(){return midAboveSma;}
    @NonDet public boolean midBelowSma(){return midBelowSma;}


    private void updateMarketDataLog(){
        System.out.println("QUOT: "+ DataSource.getTimestamp().toLocalTime()+" mid="+mid+" bid="+bid+" ask="+ask+" pnl="+closedPnl+" "+Thread.currentThread());
        System.out.println("AVER: "+ DataSource.getTimestamp().toLocalTime()+" average="+average+" midAboveSma="+midAboveSma+" midBelowSma="+midBelowSma+" midEqualSma="+midEqualSma+" pnl="+closedPnl+" "+Thread.currentThread());
        System.out.println("SESH: "+ DataSource.getTimestamp().toLocalTime()+" isOpen="+isOpen()+" isMidday="+isMidday()+" isClose="+isClose()+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    private void resetMarketDataLog(){
        System.out.println("RSET: "+ DataSource.getTimestamp().toLocalTime()+" mid="+mid+" bid="+bid+" ask="+ask+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    private void enterBuyLog(){
        System.out.println("BOUG: "+ DataSource.getTimestamp().toLocalTime()+" opened BUY at "+open+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    private void enterSellLog(){
        System.out.println("SOLD: "+ DataSource.getTimestamp().toLocalTime()+" opened SELL at "+open+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    private void closeBuyLog(){
        System.out.println("CLOS: "+ DataSource.getTimestamp().toLocalTime()+" closed BUY at "+close+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    private void closeSellLog(){
        System.out.println("CLOS: "+ DataSource.getTimestamp().toLocalTime()+" closed SELL at "+close+" pnl="+closedPnl+" "+Thread.currentThread());
    }

    @Override
    @External
    public boolean updateable() {
        return DataSource.hasNextPrice();
    }

    @Update
    @External
    public void update() {
        currentHour = DataSource.incrementAndGetTimestamp().getHour();
        mid = DataSource.getMid();
        average = DataSource.getMidIfAvgIsZero(average);
        bid = mid - 0.0001f;
        ask = mid + 0.0001f;
        final float alpha = 0.3f;
        average = (1 - alpha) * average + alpha * mid;
        midAboveSma = mid > average;
        midBelowSma =  mid < average;
        midEqualSma =  mid == average;
        updateMarketDataLog();
        assert(midBelowSma() || midAboveSma() || midEqualSma());
    }
}

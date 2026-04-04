package com.cognitionbox.petra.examples.trailingstoploss;

import com.cognitionbox.petra.examples.trailingstoploss.view.Direction;
import com.cognitionbox.petra.examples.trailingstoploss.view.DirectionUpdated;
import com.cognitionbox.petra.examples.trailingstoploss.view.PriceUpdated;
import com.cognitionbox.petra.examples.trailingstoploss.view.StopLoss;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

public final class TrailingStopLoss {
    private final Direction direction = new Direction();
    private final StopLoss stopLoss = new StopLoss();

    public boolean noDirection(){return direction.none() && stopLoss.noChange();}

    public boolean buy(){return direction.buy() && stopLoss.noChange();}

    public boolean sell(){return direction.sell() && stopLoss.noChange();}

    public boolean newMax(){return direction.buy() && stopLoss.newMax();}

    public boolean newMin(){return direction.sell() && stopLoss.newMin();}

    public boolean updatedBuyStop(){return direction.buy() && stopLoss.buyStopUpdated();}

    public boolean updatedSellStop(){return direction.sell() && stopLoss.sellStopUpdated();}

    public boolean hitStop(){return stopLoss.hitStop();}

    public boolean stopped(){return stopLoss.stopped();}

    public void selectBuy(){
        if (noDirection()){
            sep(()->{
                direction.selectBuy();});
            assert(buy());
        }
    }

    public void selectSell(){
        if (noDirection()){
            sep(()->{
                direction.selectSell();});
            assert(sell());
        }
    }
    

    public void update(){
        if (buy() || sell()){
            ;
            assert(buy() || sell());
        } else if (newMax()){
            sep(()->{
                stopLoss.updateBuyStop();});
            assert(updatedBuyStop());
        } else if (newMin()){
            sep(()->{
                stopLoss.updateSellStop();});
            assert(updatedSellStop());
        } else if (hitStop()){
            sep(()->{
                stopLoss.stop();});
            assert(stopped());
        }
    }
}

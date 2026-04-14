package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.ast.interp.util.reactive.EntryPoint;
import com.cognitionbox.petra.ast.terms.Entry;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;
import com.cognitionbox.petra.examples.trading.strategy.MarketOrder;
import com.cognitionbox.petra.examples.trading.strategy.market.views.phase.TradingDay;
import com.cognitionbox.petra.examples.trading.strategy.market.views.sma.MovingAverage;

@Entry
public final class TrendMeanRevStrategy implements EntryPoint {

    private final MovingAverage movingAverage = new MovingAverage();
    private final TradingDay tradingDay = new TradingDay();
    private final MarketOrder marketOrder = new MarketOrder();

    @Initial
    public boolean hold() {
        return (movingAverage.midEqualSma() || tradingDay.none()); }

    @NonDet
    public boolean buyTrend() {
        return movingAverage.midAboveSma() &&
                (marketOrder.sold() || marketOrder.closed()) &&
                (tradingDay.open() || tradingDay.close()); }

    @NonDet
    public boolean sellTrend() {
        return movingAverage.midBelowSma() &&
                (marketOrder.bought() || marketOrder.closed()) &&
                (tradingDay.open() || tradingDay.close()); }

    @NonDet
    public boolean buyMeanRev() {
        return movingAverage.midBelowSma() &&
                (marketOrder.sold() || marketOrder.closed()) &&
                tradingDay.midday(); }

    @NonDet
    public boolean sellMeanRev() {
        return movingAverage.midAboveSma() &&
                (marketOrder.bought() || marketOrder.closed()) &&
                tradingDay.midday(); }

    public boolean holdBoughtTrend() {
        return movingAverage.midAboveSma() &&
                marketOrder.bought() &&
                (tradingDay.open() || tradingDay.close()); }

    public boolean holdSoldTrend() {
        return movingAverage.midBelowSma() &&
                marketOrder.sold() &&
                (tradingDay.open() || tradingDay.close()); }

    public boolean holdBoughtMeanRev() {
        return movingAverage.midBelowSma() &&
                marketOrder.bought() &&
                tradingDay.midday(); }

    public boolean holdSoldMeanRev() {
        return movingAverage.midAboveSma() &&
                marketOrder.sold() &&
                tradingDay.midday(); }

    @Entry
    public void main(){
        if (buyTrend()){
            marketOrder.close();
            marketOrder.buy();
            assert(holdBoughtTrend());
        } else if (sellTrend()){
            marketOrder.close();
            marketOrder.sell();
            assert(holdSoldTrend());
        } else if (buyMeanRev()){
            marketOrder.close();
            marketOrder.buy();
            assert(holdBoughtMeanRev());
        } else if (sellMeanRev()){
            marketOrder.close();
            marketOrder.sell();
            assert(holdSoldMeanRev());
        } else if (hold()){
            ;
            assert(hold());
        }
    }
}

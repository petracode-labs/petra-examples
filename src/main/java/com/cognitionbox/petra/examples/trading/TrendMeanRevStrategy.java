package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.examples.trading.strategy.MarketData;
import com.cognitionbox.petra.examples.trading.strategy.MarketOrder;
import com.cognitionbox.petra.examples.trading.strategy.market.views.phase.TradingDay;
import com.cognitionbox.petra.examples.trading.strategy.market.views.sma.MovingAverage;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

public final class TrendMeanRevStrategy implements Runnable {
  private final MovingAverage movingAverage = new MovingAverage();
  private final TradingDay tradingDay = new TradingDay();
  private final MarketOrder marketOrder = new MarketOrder();
  private final MarketData marketData = new MarketData();

  @Initial
  public boolean start(){
      return movingAverage.midEqualSma() &&
          marketOrder.closed() &&
              tradingDay.none() &&
              marketData.cleared();}

  public boolean hold() {
        return (movingAverage.midEqualSma() || tradingDay.none()) &&
                marketData.updated();}

  public boolean buyTrend() {
    return movingAverage.midAboveSma() &&
            (marketOrder.bought() || marketOrder.sold() || marketOrder.closed()) &&
            (tradingDay.open() || tradingDay.close()) &&
            marketData.updated();}

  public boolean sellTrend() {
    return movingAverage.midBelowSma() &&
            (marketOrder.bought() || marketOrder.sold() || marketOrder.closed()) &&
            (tradingDay.open() || tradingDay.close()) &&
            marketData.updated();}

  public boolean buyMeanRev() {
    return movingAverage.midBelowSma() &&
            (marketOrder.bought() || marketOrder.sold() || marketOrder.closed()) &&
            tradingDay.midday() &&
            marketData.updated();}

  public boolean sellMeanRev() {
    return movingAverage.midAboveSma() &&
            (marketOrder.bought() || marketOrder.sold() || marketOrder.closed()) &&
            tradingDay.midday() &&
            marketData.updated();}

  public boolean holdBoughtTrend() {
    return movingAverage.midAboveSma() &&
            marketOrder.bought() &&
            (tradingDay.open() || tradingDay.close()) &&
            marketData.cleared();
  }

  public boolean holdSoldTrend() {
    return movingAverage.midBelowSma() &&
            marketOrder.sold() &&
            (tradingDay.open() || tradingDay.close()) &&
            marketData.cleared();
  }

  public boolean holdBoughtMeanRev() {
    return movingAverage.midBelowSma() &&
            marketOrder.bought() &&
            tradingDay.midday() &&
            marketData.cleared();
  }

  public boolean holdSoldMeanRev() {
    return movingAverage.midAboveSma() &&
            marketOrder.sold() &&
            tradingDay.midday() &&
            marketData.cleared();
  }

  public void run(){
    if (start()){
        marketData.update();
        assert(hold() || buyTrend() || sellTrend() || buyMeanRev() || sellMeanRev());
    } else if (hold()){
        marketData.clear();marketData.update();
        assert(hold() || buyTrend() || sellTrend() || buyMeanRev() || sellMeanRev());
    } else if (buyTrend()){
        sep(()->{marketOrder.close();marketOrder.buy();},
            ()->{marketData.clear();});
      assert(holdBoughtTrend());
    } else if (sellTrend()){
        sep(()->{marketOrder.close();marketOrder.sell();},
            ()->{marketData.clear();});
      assert(holdSoldTrend());
    } else if (buyMeanRev()){
        sep(()->{marketOrder.close();marketOrder.buy();},
            ()->{marketData.clear();});
      assert(holdBoughtMeanRev());
    } else if (sellMeanRev()){
        sep(()->{marketOrder.close();marketOrder.sell();},
            ()->{marketData.clear();});
      assert(holdSoldMeanRev());
    }
    else if (holdBoughtTrend() || holdSoldTrend() || holdBoughtMeanRev() || holdSoldMeanRev()){
        marketData.update();
        assert(hold() || buyTrend() || sellTrend() || buyMeanRev() || sellMeanRev());
    }
  }
}

package com.cognitionbox.petra.examples.trading.strategy.market.views.phase;

import com.cognitionbox.petra.examples.trading.strategy.order.Order;

import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

public final class TradingDay {
    private final Order order = singleton(Order.class);
  public boolean open(){return order.isOpen();}
  public boolean midday(){return order.isMidday();}
  public boolean close(){return order.isClose();}
  public boolean none(){return order.isNone();}
}

package com.cognitionbox.petra.examples.trading.strategy.market.views.sma;

import com.cognitionbox.petra.examples.trading.strategy.order.Order;

import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

public final class MovingAverage {
    private final Order order = singleton(Order.class);
    public boolean midAboveSma(){return order.midAboveSma();}
    public boolean midBelowSma(){return order.midBelowSma();}
    public boolean midEqualSma() {return order.midEqualSma();}
}
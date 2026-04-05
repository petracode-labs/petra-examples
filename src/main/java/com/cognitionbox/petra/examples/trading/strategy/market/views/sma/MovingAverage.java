package com.cognitionbox.petra.examples.trading.strategy.market.views.sma;

import com.cognitionbox.petra.examples.trading.strategy.order.Order;

import static com.cognitionbox.petra.ast.interp.util.Singleton.view;

public final class MovingAverage {
    private final Order order = view(Order::new);
    public boolean midAboveSma(){return order.midAboveSma();}
    public boolean midBelowSma(){return order.midBelowSma();}
    public boolean midEqualSma() {return order.midEqualSma();}
}
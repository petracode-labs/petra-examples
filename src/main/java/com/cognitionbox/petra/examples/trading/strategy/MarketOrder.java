package com.cognitionbox.petra.examples.trading.strategy;

import com.cognitionbox.petra.examples.trading.strategy.order.Order;

import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

public final class MarketOrder {
    private final Order order = singleton(Order.class);

    public boolean bought(){
        return order.bought();
    }

    public boolean sold(){
        return order.sold();
    }

    public boolean closed(){
        return (order.closedEarly());
    }

    public void close(){
        if (bought() ^ sold()){
            order.closeEarly();
            assert(closed());
        } else if (closed()){
            ;
            assert(closed());
        }
    }

    public void buy(){
        if (closed()){
            order.enterBuy();
            assert(bought());
        }
    }

    public void sell(){
        if (closed()){
            order.enterSell();
            assert(sold());
        }
    }
}

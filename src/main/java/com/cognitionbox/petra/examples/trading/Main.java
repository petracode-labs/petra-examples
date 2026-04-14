package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.examples.trading.strategy.data.DataSource;

import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;
import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;
import com.cognitionbox.petra.examples.trading.strategy.order.Order;

public class Main {
    public static void main(String[] args) {
        DataSource.loadMarketData("src/test/resources/21/EURUSD_Candlestick_1_Hour_BID_02.06.2003-16.06.2018.csv");
        startReactive(new TrendMeanRevStrategy(),  singleton(Order.class));
    }
}

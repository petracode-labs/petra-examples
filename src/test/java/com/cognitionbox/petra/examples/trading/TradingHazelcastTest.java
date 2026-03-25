package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.examples.trading.strategy.data.DataSource;
import com.cognitionbox.petra.examples.trading.strategy.order.Order;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TradingHazelcastTest {

    private HazelcastInstance hazelcastInstance;

    @Before
    public void setUp() {
        // Starts a local Hazelcast member using the hazelcast.xml in your classpath
        hazelcastInstance = Hazelcast.newHazelcastInstance();
    }

    @After
    public void tearDown() {
        // Shutdown the instance after the test to free up ports and memory
        hazelcastInstance.shutdown();
    }

    @Test
    public void testOrderPersistence() {
        // 1. Create the order and perform an action
        Order order = new Order();
        
        // Assuming your updateMarketData or enterBuy calls persist()
        order.enterBuy();
        
        // 2. Verify local state
        assertTrue("Quote should be updated", order.bought());
        
        // 3. Create a second Order object (simulating a restart)
        // This new object should pull the data from the IMap in its constructor
        Order recoveredOrder = new Order();
        
        assertEquals("Recovered order should have same state", 
                     order.quoteUpdated(), recoveredOrder.quoteUpdated());
    }

    @Test
    public void testTrading() {
        DataSource.loadMarketData("src/test/resources/21/EURUSD_Candlestick_1_Hour_BID_02.06.2003-16.06.2018.csv");
        TrendMeanRevStrategy strategy = new TrendMeanRevStrategy();
        while(DataSource.hasNextPrice()){
            strategy.run();
        }
    }
}
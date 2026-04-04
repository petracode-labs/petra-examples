package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.TrafficAgent;
import com.cognitionbox.petra.examples.trading.strategy.data.Singleton;

public class TrafficAgentColourView {
    private final TrafficAgent agent = Singleton.get(TrafficAgent.class);

    public boolean wantsGreen() { return agent.wantsGreen(); }
    public boolean wantsRed() { return agent.wantsRed(); }

    // Colour mutations
    public void forceRed() {
        if (wantsGreen()) { // Pre-condition
            agent.forceRed();
            assert (wantsRed()); // Post-condition
        }
    }
}
package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.PedestrianAgent;
import com.cognitionbox.petra.examples.trading.strategy.data.Singleton;

public class PedestrianAgentColourView {
    private final PedestrianAgent agent = Singleton.get(PedestrianAgent.class);

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
package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.TrafficAgent;

import static com.cognitionbox.petra.ast.interp.util.Singleton.view;

public class TrafficAgentColourView {
    private final TrafficAgent agent = view(TrafficAgent::new);

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
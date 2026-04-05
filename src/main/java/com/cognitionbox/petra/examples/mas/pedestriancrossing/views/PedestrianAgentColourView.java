package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.PedestrianAgent;

import static com.cognitionbox.petra.ast.interp.util.Singleton.view;

public class PedestrianAgentColourView {
    private final PedestrianAgent agent = view(PedestrianAgent::new);

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
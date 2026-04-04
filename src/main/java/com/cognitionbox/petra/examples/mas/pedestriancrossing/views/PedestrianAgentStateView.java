package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.PedestrianAgent;
import com.cognitionbox.petra.examples.trading.strategy.data.Singleton;

public class PedestrianAgentStateView {
    private final PedestrianAgent agent = Singleton.get(PedestrianAgent.class);

    public boolean waiting() { return agent.waiting(); }
    public boolean thinking() { return agent.thinking(); }
    public boolean decided() { return agent.decided(); }
    public boolean signalled() { return agent.signalled(); }

    // State transition mutations
    public void think() {
        if (waiting()) { // Pre-condition
            agent.start();
            assert (thinking()); // Post-condition
        }
    }

    public void act() {
        if (thinking()) { // Pre-condition
            agent.act();
            assert(decided()); // Post-condition
        }
    }

    public void signal() {
        if (decided()) { // Pre-condition
            agent.signal();
            assert (signalled()); // Post-condition
        }
    }

    public void reset() {
        if (signalled()) { // Pre-condition
            agent.reset();
            assert (waiting()); // Post-condition
        }
    }
}
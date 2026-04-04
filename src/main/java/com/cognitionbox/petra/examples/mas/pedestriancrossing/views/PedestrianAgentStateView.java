package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.PedestrianAgent;
import com.cognitionbox.petra.examples.trading.strategy.data.Singleton;

public class PedestrianAgentStateView {
    private final PedestrianAgent agent = Singleton.get(PedestrianAgent.class);

    public boolean isWaiting() { return agent.isWaiting(); }
    public boolean isThinking() { return agent.isThinking(); }
    public boolean isDecided() { return agent.isDecided(); }
    public boolean isSignalled() { return agent.isSignalled(); }

    // State transition mutations
    public void start() {
        if (isWaiting()) { // Pre-condition
            agent.start();
            assert (isThinking()); // Post-condition
        }
    }

    public void act() {
        if (isThinking()) { // Pre-condition
            agent.act();
            assert(isDecided()); // Post-condition
        }
    }

    public void signal() {
        if (isDecided()) { // Pre-condition
            agent.signal();
            assert (isSignalled()); // Post-condition
        }
    }

    public void reset() {
        if (isSignalled()) { // Pre-condition
            agent.reset();
            assert (isWaiting()); // Post-condition
        }
    }
}
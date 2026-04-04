package com.cognitionbox.petra.examples.mas.pedestriancrossing.views;

import com.cognitionbox.petra.examples.mas.pedestriancrossing.TrafficAgent;
import com.cognitionbox.petra.examples.trading.strategy.data.Singleton;

public class TrafficAgentStateView {
    private final TrafficAgent agent = Singleton.get(TrafficAgent.class);

    public boolean isWaiting() { return agent.waiting(); }
    public boolean isThinking() { return agent.thinking(); }
    public boolean isDecided() { return agent.decided(); }
    public boolean isSignalled() { return agent.signalled(); }

    // State transition mutations
    public void think() {
        if (isWaiting()) { // Pre-condition
            agent.start();
            assert (isThinking()); // Post-condition
        }
    }

    public void act() {
        if (isThinking()) { // Pre-condition
            agent.act();
            assert (isDecided()); // Post-condition
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
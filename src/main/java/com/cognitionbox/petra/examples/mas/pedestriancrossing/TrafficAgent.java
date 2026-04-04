package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.data.Colour;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.data.State;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.external.CustomLogger;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.external.LLM;

// --- TRAFFIC AGENT ---
@Base
public class TrafficAgent {

    private State status = State.WAITING;
    private Colour colour = Colour.SET_RED;
    private final LLM tool = new LLM();

    @Initial
    public boolean isWaiting() { return status == State.WAITING; }
    public boolean isThinking() { return status == State.THINKING; }
    public boolean isDecided() { return status == State.DECIDED; }
    public boolean isSignalled() { return status == State.SIGNALLED; }
    public boolean isFailed() { return status == State.FAILED; }

    public boolean wantsGreen() { return  colour == Colour.SET_GREEN; }
    public boolean wantsRed() { return colour == Colour.SET_RED; }

    public void start() {
        if (isWaiting()) {
            status = State.THINKING;
            assert (isThinking());
        }
    }

    public void act() {
        if (isThinking()) {
            try {
                this.colour = Colour.valueOf(tool.askLLM("You are the Palmers Green crossing traffic light controller. It's critical that the traffic and pedestrian crossing lights cannot both be SET_GREEN at the same time. Currently the traffic light is "+colour+", balance the flow of people and cars, and ensure no accidents by responding only with one of SET_GREEN or SET_RED."));
            } catch (Exception e) {
                e.printStackTrace();
                CustomLogger.log("TrafficAgent: will fail-safe to SET_RED due to Error with call to LLM");
                this.colour = Colour.SET_RED; // failsafe
            }
            status = State.DECIDED;
            assert(isDecided());
        }
    }

    public void forceRed() {
        if (wantsGreen()){
            CustomLogger.log("TrafficAgent: will fail-safe to SET_RED due to both agents returning SET_GREEN");
            this.colour = Colour.SET_RED;
            assert(wantsRed());
        }
    }

    public void signal() {
        if (isDecided()) {
            CustomLogger.log("TrafficAgent: signal changed to " + colour);
            status = State.SIGNALLED;
            assert(isSignalled());
        }
    }

    public void reset() {
        if (isSignalled()) {
            status = State.WAITING;
            CustomLogger.log("TrafficAgent: reset");
            assert(isWaiting());
        }
    }
}
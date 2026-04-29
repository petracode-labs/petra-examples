package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import com.cognitionbox.petra.ast.interp.util.reactive.Updateable;
import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.data.State;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.external.CustomLogger;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.external.LLM;

// --- PEDESTRIAN AGENT ---
@Base
public class PedestrianAgent implements Updateable {

    private State status = State.WAITING;
    private final LLM tool = new LLM();

    @Initial
    public boolean noDecision() { return status == State.WAITING; }

    @NonDet public boolean wantsGreen() { return status == State.SET_GREEN; }
    @NonDet public boolean wantsRed() { return status == State.SET_RED; }

    public boolean colourUpdated() { return status == State.SIGNALLED; }

    public void forceRed() {
        if (wantsGreen()){
            CustomLogger.log("PedestrianAgent: will fail-safe to SET_RED due to both agents returning SET_GREEN");
            this.status = State.SET_RED;
            assert(wantsRed());
        }
    }

    public void updateLightColour() {
        if (wantsGreen() || wantsRed()) {
            CustomLogger.log("PedestrianAgent: signal changed to " + status);
            status = State.SIGNALLED;
            assert(colourUpdated());
        }
    }

    public void reset() {
        if (colourUpdated()) {
            status = State.WAITING;
            CustomLogger.log("TrafficAgent: reset");
            assert(noDecision());
        }
    }

    @Override
    @External
    public boolean updateable() {
        return true;
    }

    @Override
    @External
    public void update() {
        try {
            this.status = State.valueOf(tool.askLLM("You are the Palmers Green crossing pedestrian light controller. It's critical that the traffic and pedestrian crossing lights cannot both be SET_GREEN at the same time.  Make sure you balance the car and pedestrian traffic. Currently the pedestrian light is "+status+", balance the flow of people and cars, and ensure no accidents by responding only with one of SET_GREEN or SET_RED."));
        } catch (Exception e) {
            e.printStackTrace();
            CustomLogger.log("PedestrianAgent: will fail-safe to SET_RED due to Error with call to LLM");
            this.status = State.SET_RED; // failsafe
        }
        assert(wantsGreen() || wantsRed());
    }
}
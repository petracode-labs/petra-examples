package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import com.cognitionbox.petra.ast.interp.util.reactive.EntryPoint;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;

import static com.cognitionbox.petra.ast.interp.util.Program.seq;
import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

// --- MAS CONTROLLER ---
public class TrafficControlMAS implements EntryPoint {

    // all base objects must be instantiated as singletons
    private final PedestrianAgent pedestrianAgent = singleton(PedestrianAgent.class);
    private final TrafficAgent trafficAgent = singleton(TrafficAgent.class);

    @Initial
    @NonDet public boolean start() {
        return pedestrianAgent.noDecision() && trafficAgent.noDecision();
    }

    @NonDet public boolean bothWantGreen() {
        return pedestrianAgent.wantsGreen() && trafficAgent.wantsGreen();
    }

    // IMPORTANT: Required to handle failsafe correctly
    @NonDet public boolean bothWantRed() {
        return pedestrianAgent.wantsRed() && trafficAgent.wantsRed();
    }

    @NonDet public boolean bothWantDifferent() {
        return ((pedestrianAgent.wantsRed() && trafficAgent.wantsGreen()) || (pedestrianAgent.wantsGreen() && trafficAgent.wantsRed())) ;
    }

    public boolean signalled() {
        return pedestrianAgent.colourUpdated() && trafficAgent.colourUpdated();
    }

    public void main() {
        if (bothWantGreen()) {
            trafficAgent.forceRed();
            assert(bothWantDifferent());
        }
        else if (bothWantDifferent()) {
            seq(()->pedestrianAgent.updateLightColour(),()->trafficAgent.updateLightColour());
            assert(signalled());
        }
        else if (bothWantRed()) {
            seq(()->pedestrianAgent.updateLightColour(),()->trafficAgent.updateLightColour());
            assert(signalled());
        }
        else if (signalled()) {
            seq(()->pedestrianAgent.reset(),()->trafficAgent.reset());
            assert(start());
        }
    }
}
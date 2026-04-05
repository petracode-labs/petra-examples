package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import com.cognitionbox.petra.ast.interp.util.reactive.EntryPoint;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.PedestrianAgentColourView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.PedestrianAgentStateView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.TrafficAgentColourView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.TrafficAgentStateView;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

// --- MAS CONTROLLER ---
public class TrafficControlMAS implements EntryPoint {

    // Pair 1: Pedestrian Views (sharing the single PedestrianAgent)
    private final PedestrianAgentStateView pedState = new PedestrianAgentStateView();
    private final PedestrianAgentColourView pedColour = new PedestrianAgentColourView();

    // Pair 2: Traffic Views (sharing the single TrafficAgent)
    private final TrafficAgentStateView carState = new TrafficAgentStateView();
    private final TrafficAgentColourView carColour = new TrafficAgentColourView();

    @Initial
    public boolean start() {
        return pedState.waiting() && carState.isWaiting();
    }

    public boolean agentsThinking() {
        return pedState.thinking() && carState.isThinking();
    }

    public boolean bothWantGreen() {
        return pedState.decided() && carState.isDecided() && pedColour.wantsGreen() && carColour.wantsGreen();
    }

    // IMPORTANT: Required to handle failsafe correctly
    public boolean bothWantRed() {
        return pedState.decided() && carState.isDecided() && pedColour.wantsRed() && carColour.wantsRed();
    }

    public boolean bothWantDifferent() {
        return pedState.decided() && carState.isDecided() && ((pedColour.wantsRed() && carColour.wantsGreen()) || (pedColour.wantsGreen() && carColour.wantsRed())) ;
    }

    public boolean signalled() {
        return pedState.signalled() && carState.isSignalled();
    }

    public void main() {
        if (start()) {
            sep(()->pedState.think(), ()->carState.think());
            assert(agentsThinking());
        }
        else if (bothWantGreen()) {
            carColour.forceRed();
            assert(bothWantDifferent());
        }
        else if (bothWantDifferent()) {
            sep(()->pedState.signal(), ()->carState.signal());
            assert(signalled());
        }
        else if (bothWantRed()) {
            sep(()->pedState.signal(), ()->carState.signal());
            assert(signalled());
        }
        else if (signalled()) {
            sep(()->pedState.reset(), ()->carState.reset());
            assert(start());
        }
    }
}
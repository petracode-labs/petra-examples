package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.PedestrianAgentColourView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.PedestrianAgentStateView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.TrafficAgentColourView;
import com.cognitionbox.petra.examples.mas.pedestriancrossing.views.TrafficAgentStateView;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

// --- MAS CONTROLLER ---
public class TrafficControlMAS {

    // Pair 1: Pedestrian Views (sharing the single PedestrianAgent)
    private final PedestrianAgentStateView pedState = new PedestrianAgentStateView();
    private final PedestrianAgentColourView pedColour = new PedestrianAgentColourView();

    // Pair 2: Traffic Views (sharing the single TrafficAgent)
    private final TrafficAgentStateView carState = new TrafficAgentStateView();
    private final TrafficAgentColourView carColour = new TrafficAgentColourView();

    @Initial
    public boolean init() {
        return pedState.isWaiting() && carState.isWaiting();
    }

    public boolean agentsThinking() {
        return pedState.isThinking() && carState.isThinking();
    }

    public boolean bothWantGreen() {
        return pedState.isDecided() && carState.isDecided() && pedColour.wantsGreen() && carColour.wantsGreen();
    }

    // IMPORTANT: Required to handle failsafe correctly
    public boolean bothWantRed() {
        return pedState.isDecided() && carState.isDecided() && pedColour.wantsRed() && carColour.wantsRed();
    }

    public boolean bothWantDifferent() {
        return pedState.isDecided() && carState.isDecided() && ((pedColour.wantsRed() && carColour.wantsGreen()) || (pedColour.wantsGreen() && carColour.wantsRed())) ;
    }

    public boolean signalled() {
        return pedState.isSignalled() && carState.isSignalled();
    }

    public void act() {
        if (init()) {
            sep(()->pedState.start(), ()->carState.start());
            assert(agentsThinking());
        }
        else if (agentsThinking()) {
            sep(()->pedState.act(), ()->carState.act());
            assert(bothWantGreen() || bothWantRed() || bothWantDifferent());
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
            assert(init());
        }
    }
}
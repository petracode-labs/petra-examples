package com.cognitionbox.petra.examples.mas.researchlab;

import com.cognitionbox.petra.ast.interp.util.reactive.EntryPoint;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;

import static com.cognitionbox.petra.ast.interp.util.Program.seq;
import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

// --- MAS CONTROLLER ---
// Encodes the release-authorization gate of an autonomous research lab.
// Safety property: an artifact is published only when the lab requests
// release AND the owner authorizes it in the same round; every other
// combination of decisions (or any LLM failure) resolves to private.
public class ReleaseGateMAS implements EntryPoint {

    // all base objects must be instantiated as singletons
    private final LabAgent labAgent = singleton(LabAgent.class);
    private final OwnerAgent ownerAgent = singleton(OwnerAgent.class);

    @Initial
    @NonDet public boolean start() {
        return labAgent.noDecision() && ownerAgent.noDecision();
    }

    // hazard: the lab wants to publish but the owner has not authorized it
    @NonDet public boolean unauthorizedRelease() {
        return labAgent.wantsRelease() && ownerAgent.wantsHold();
    }

    // stale authorization: the owner authorized but the lab is not releasing
    @NonDet public boolean unusedAuthorization() {
        return labAgent.wantsHold() && ownerAgent.wantsRelease();
    }

    // the only combination that may reach publishArtifact()
    @NonDet public boolean authorizedRelease() {
        return labAgent.wantsRelease() && ownerAgent.wantsRelease();
    }

    @NonDet public boolean bothHold() {
        return labAgent.wantsHold() && ownerAgent.wantsHold();
    }

    public boolean resolved() {
        return labAgent.decisionResolved() && ownerAgent.decisionResolved();
    }

    public void main() {
        if (unauthorizedRelease()) {
            labAgent.forceHold();
            assert(bothHold());
        }
        else if (unusedAuthorization()) {
            ownerAgent.forceHold();
            assert(bothHold());
        }
        else if (authorizedRelease()) {
            seq(()->labAgent.publishArtifact(),()->ownerAgent.recordDecision());
            assert(resolved());
        }
        else if (bothHold()) {
            seq(()->labAgent.archivePrivately(),()->ownerAgent.recordDecision());
            assert(resolved());
        }
        else if (resolved()) {
            seq(()->labAgent.reset(),()->ownerAgent.reset());
            assert(start());
        }
    }
}

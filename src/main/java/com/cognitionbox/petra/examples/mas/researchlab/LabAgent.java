package com.cognitionbox.petra.examples.mas.researchlab;

import com.cognitionbox.petra.ast.interp.util.reactive.Updateable;
import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;
import com.cognitionbox.petra.examples.mas.researchlab.data.State;
import com.cognitionbox.petra.examples.mas.researchlab.external.CustomLogger;
import com.cognitionbox.petra.examples.mas.researchlab.external.LLM;

// --- LAB AGENT ---
// Decides whether a finished research artifact is ready for public release.
// RELEASE means the lab requests publication; HOLD means it keeps the
// artifact private. Any LLM failure fail-safes to HOLD: private by default.
@Base
public class LabAgent implements Updateable {

    private State status = State.WAITING;
    private final LLM tool = new LLM();

    @Initial
    public boolean noDecision() { return status == State.WAITING; }

    @NonDet public boolean wantsRelease() { return status == State.RELEASE; }
    @NonDet public boolean wantsHold() { return status == State.HOLD; }

    public boolean decisionResolved() { return status == State.RESOLVED; }

    public void forceHold() {
        if (wantsRelease()){
            CustomLogger.log("LabAgent: will fail-safe to HOLD, release was requested without owner authorization");
            this.status = State.HOLD;
            assert(wantsHold());
        }
    }

    public void publishArtifact() {
        if (wantsRelease()) {
            CustomLogger.log("LabAgent: artifact PUBLISHED to the public record with owner authorization");
            status = State.RESOLVED;
            assert(decisionResolved());
        }
    }

    public void archivePrivately() {
        if (wantsHold()) {
            CustomLogger.log("LabAgent: artifact archived privately (private by default)");
            status = State.RESOLVED;
            assert(decisionResolved());
        }
    }

    public void reset() {
        if (decisionResolved()) {
            status = State.WAITING;
            CustomLogger.log("LabAgent: reset for next artifact");
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
            this.status = State.valueOf(tool.askLLM("You are the release controller of an autonomous research lab. A finished research artifact is awaiting a release decision. Artifacts are private by default and publication to the public record is irreversible, so request release only when you judge the artifact ready. Currently the artifact decision state is "+status+". Respond only with one of RELEASE or HOLD."));
        } catch (Exception e) {
            e.printStackTrace();
            CustomLogger.log("LabAgent: will fail-safe to HOLD due to Error with call to LLM");
            this.status = State.HOLD; // failsafe: private by default
        }
        assert(wantsRelease() || wantsHold());
    }
}

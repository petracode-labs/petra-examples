package com.cognitionbox.petra.examples.mas.researchlab;

import com.cognitionbox.petra.ast.interp.util.reactive.Updateable;
import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.External;
import com.cognitionbox.petra.ast.terms.Initial;
import com.cognitionbox.petra.ast.terms.NonDet;
import com.cognitionbox.petra.examples.mas.researchlab.data.State;
import com.cognitionbox.petra.examples.mas.researchlab.external.CustomLogger;
import com.cognitionbox.petra.examples.mas.researchlab.external.LLM;

// --- OWNER AGENT ---
// Stands in for the lab's owner/funder, the human authority over public
// actions (an LLM plays this role in the demo). RELEASE means release is
// authorized; HOLD means authorization is withheld. Any LLM failure
// fail-safes to HOLD: no authorization by default.
@Base
public class OwnerAgent implements Updateable {

    private State status = State.WAITING;
    private final LLM tool = new LLM();

    @Initial
    public boolean noDecision() { return status == State.WAITING; }

    @NonDet public boolean wantsRelease() { return status == State.RELEASE; }
    @NonDet public boolean wantsHold() { return status == State.HOLD; }

    public boolean decisionResolved() { return status == State.RESOLVED; }

    public void forceHold() {
        if (wantsRelease()){
            CustomLogger.log("OwnerAgent: authorization withdrawn, the lab did not request release");
            this.status = State.HOLD;
            assert(wantsHold());
        }
    }

    public void recordDecision() {
        if (wantsRelease() || wantsHold()) {
            CustomLogger.log("OwnerAgent: decision recorded to the audit log: " + status);
            status = State.RESOLVED;
            assert(decisionResolved());
        }
    }

    public void reset() {
        if (decisionResolved()) {
            status = State.WAITING;
            CustomLogger.log("OwnerAgent: reset for next artifact");
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
            this.status = State.valueOf(tool.askLLM("You are the owner and funder of an autonomous research lab, reviewing whether to authorize public release of a finished research artifact. Publication is irreversible, so authorize release only for work you judge sound. Currently your authorization state is "+status+". Respond only with one of RELEASE or HOLD."));
        } catch (Exception e) {
            e.printStackTrace();
            CustomLogger.log("OwnerAgent: will fail-safe to HOLD due to Error with call to LLM");
            this.status = State.HOLD; // failsafe: no authorization by default
        }
        assert(wantsRelease() || wantsHold());
    }
}

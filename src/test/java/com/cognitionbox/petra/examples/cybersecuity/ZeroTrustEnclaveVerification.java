package com.cognitionbox.petra.examples.cybersecuity;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class ZeroTrustEnclaveVerification extends PetraVerification {
    public ZeroTrustEnclaveVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyJava("com/cognitionbox/petra/examples/cybersecurity/", "ZeroTrustEnclave", Mode.COORD_BASE);

    }
}

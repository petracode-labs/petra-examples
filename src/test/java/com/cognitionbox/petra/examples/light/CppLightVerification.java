package com.cognitionbox.petra.examples.light;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

//@RunWith(PetraVerificationRunner.class)
@RunWith(Parameterized.class)
public class CppLightVerification extends PetraVerification {
    public CppLightVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyCoord("com/cognitionbox/petra/examples/light/", "Light", Language.CPP, Mode.VERIFY_COORD_BASE);
    }
}

package com.cognitionbox.petra.examples.light;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class LightVerification extends PetraVerification {
    public LightVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyCoord("com/cognitionbox/petra/examples/light/", "Light", Language.JAVA, Mode.VERIFY_COORD_BASE);
    }
}

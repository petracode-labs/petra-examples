package com.cognitionbox.petra.examples.drone;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class DroneCppVerification extends PetraVerification {
    public DroneCppVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyCoord("com/cognitionbox/petra/examples/drone/", "Controller", Language.CPP, Mode.ASSUME_COORD_BASE);
    }
}

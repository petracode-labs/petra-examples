package com.cognitionbox.petra.examples.tworoomalarmfailure;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class TwoRoomAlarmFailureVerification extends PetraVerification {
    public TwoRoomAlarmFailureVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(TwoRoomAlarmVerificationFailure.class);
    }
}

package com.cognitionbox.petra.examples.trailingstoploss;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import com.cognitionbox.petra.examples.trailingstoploss.TrailingStopLoss;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class TrailingStopLossVerification extends PetraVerification {
    public TrailingStopLossVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verify(TrailingStopLoss.class);
    }
}

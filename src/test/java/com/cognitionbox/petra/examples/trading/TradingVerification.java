package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class TradingVerification extends PetraVerification {
    public TradingVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyCoord("com/cognitionbox/petra/examples/trading/", "TrendMeanRevStrategy", Language.JAVA, Mode.ASSUME_COORD_BASE);
    }
}

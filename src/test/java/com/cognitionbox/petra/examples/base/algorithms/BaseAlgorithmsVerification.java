package com.cognitionbox.petra.examples.base.algorithms;

import com.cognitionbox.petra.ast.interp.PetraVerification;
import com.cognitionbox.petra.ast.interp.junit.tasks.PetraTask;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

@RunWith(Parameterized.class)
public class BaseAlgorithmsVerification extends PetraVerification {
    public BaseAlgorithmsVerification(PetraTask task) {
        super(task);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection tasks() {
        return verifyBase("com/cognitionbox/petra/examples/base/algorithms/", "BaseAlgorithms", Language.JAVA);
    }
}
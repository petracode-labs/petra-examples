package com.cognitionbox.petra.examples.andgate;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base
public final class InputSignal {
    private boolean signal = false;

    @Initial
    public boolean low() {
        return signal == false;
    }

    public boolean high() {
        return signal == true;
    }

    public void raise() {
        if (high() ^ low()) {
            signal = true;
            assert (high());
        }
    }

    public void lower() {
        if (high() ^ low()) {
            signal = false;
            assert (low());
        }
    }
}

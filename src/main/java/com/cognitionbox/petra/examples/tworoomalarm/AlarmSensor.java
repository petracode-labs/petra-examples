package com.cognitionbox.petra.examples.tworoomalarm;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base
public final class AlarmSensor {
    private boolean armed = false;

    @Initial
    public boolean disarmed() {
        return armed == false;
    }

    public boolean armed() {
        return armed == true;
    }

    public void arm() {
        if (armed() ^ disarmed()) {
            armed = true;
            assert (armed());
        }
    }

    public void disarm() {
        if (armed() ^ disarmed()) {
            armed = false;
            assert (disarmed());
        }
    }
}

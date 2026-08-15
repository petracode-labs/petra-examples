package com.cognitionbox.petra.examples.tworoomalarm;

import com.cognitionbox.petra.ast.terms.Entry;
import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

@Entry
public final class TwoRoomAlarm {
    private final AlarmSensor front = new AlarmSensor();
    private final AlarmSensor bedroom = new AlarmSensor();

    @Initial
    public boolean none() {
        return front.disarmed() && bedroom.disarmed();
    }

    public boolean frontonly() {
        return front.armed() && bedroom.disarmed();
    }

    public boolean bedroomonly() {
        return front.disarmed() && bedroom.armed();
    }

    public boolean all() {
        return front.armed() && bedroom.armed();
    }

    @Entry
    public void toggle() {
        if (none()) {
            sep(() -> front.arm(), () -> bedroom.arm());
            assert (all());
        } else if (all()) {
            bedroom.disarm();
            assert (frontonly());
        } else if (frontonly()) {
            sep(() -> front.disarm(), () -> bedroom.arm());
            assert (bedroomonly());
        } else if (bedroomonly()) {
            bedroom.disarm();
            assert (none());
        }
    }
}

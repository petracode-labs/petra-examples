package com.cognitionbox.petra.examples.tworoomalarmfailure;

import com.cognitionbox.petra.ast.terms.Entry;
import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

@Entry
public final class TwoRoomAlarmVerificationFailure {
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

    // Petra treats public boolean methods as state predicates.
    // This predicate intentionally overlaps frontonly(), bedroomonly(), and all().
    // The verifier should reject this class because the object states are not disjoint.
    public boolean protectedarea() {
        return front.armed() || bedroom.armed();
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

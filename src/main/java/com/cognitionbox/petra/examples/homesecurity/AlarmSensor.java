package com.cognitionbox.petra.examples.homesecurity;

import com.cognitionbox.petra.ast.terms.Base;
import com.cognitionbox.petra.ast.terms.Initial;

@Base public class AlarmSensor {
	private boolean power = true;
	private boolean control = false;

    @Initial
    public boolean disarmed() { return !power || !control; }
	public boolean armed() { return power && control; }


	public void arm() {
		if (armed() ^ disarmed()){
            control = true;
			assert(armed());
		}
	}

	public void disarm() {
		if (armed() ^ disarmed()){
            control = false;
			assert(disarmed());
		}
	}
}
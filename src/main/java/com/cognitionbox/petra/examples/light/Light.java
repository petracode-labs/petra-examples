package com.cognitionbox.petra.examples.light;

import com.cognitionbox.petra.ast.terms.Initial;

public final class Light {
    private final Power power = new Power();
    private final Control control = new Control();

    @Initial
    public boolean on() { return power.on() && control.on(); }
    public boolean off() { return power.on() && control.off(); }
    

    public void toggle() {
        if (off()){
            control.turnOn();
            assert(on());
        } else if (on()){
            control.turnOff();
            assert(off());
        }
    }
}

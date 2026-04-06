package com.cognitionbox.petra.examples.light;

import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.*;

public final class Light {
    private final Power power = new Power();
    private final Control control = new Control();

    @Initial
    public boolean off() { return power.off() || control.off(); }
    public boolean on() { return power.on() && control.on(); }
    

    public void toggle() {
        if (off()){
            par(()->power.turnOn(),()->control.turnOn());
            assert(on());
        } else if (on()){
            seq(()->power.turnOff(),()->control.turnOff());
            assert(off());
        }
    }
}

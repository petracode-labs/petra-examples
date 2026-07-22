package com.cognitionbox.petra.examples.andgate;

import com.cognitionbox.petra.ast.terms.Entry;
import com.cognitionbox.petra.ast.terms.Initial;

import static com.cognitionbox.petra.ast.interp.util.Program.sep;

@Entry
public final class ThreeInputAndGate {
    private final InputSignal a = new InputSignal();
    private final InputSignal b = new InputSignal();
    private final InputSignal c = new InputSignal();

    public boolean outputlow() {
        return a.low() || b.low() || c.low();
    }

    public boolean outputhigh() {
        return a.high() && b.high() && c.high();
    }

    @Initial
    public boolean none() {
        return a.low() && b.low() && c.low();
    }

    public boolean onlyahigh() {
        return a.high() && b.low() && c.low();
    }

    public boolean onlybhigh() {
        return a.low() && b.high() && c.low();
    }

    public boolean onlychigh() {
        return a.low() && b.low() && c.high();
    }

    public boolean onlyabandbhigh() {
        return a.high() && b.high() && c.low();
    }

    public boolean onlyaandchigh() {
        return a.high() && b.low() && c.high();
    }

    public boolean onlybandchigh() {
        return a.low() && b.high() && c.high();
    }

    public boolean all() {
        return a.high() && b.high() && c.high();
    }

    @Entry
    public void toggle() {
        if (none()) {
            sep(() -> a.raise(), () -> b.raise(), () -> c.raise());
            assert (all() && outputhigh());
        } else if (all()) {
            sep(() -> a.lower(), () -> b.lower(), () -> c.lower());
            assert (none() && outputlow());
        }
    }
}

package com.cognitionbox.petra.examples.andgate;

public class Main {
    public static void main(String[] args) {
        ThreeInputAndGate gate = new ThreeInputAndGate();

        gate.toggle();
        gate.toggle();
        gate.toggle();
    }
}

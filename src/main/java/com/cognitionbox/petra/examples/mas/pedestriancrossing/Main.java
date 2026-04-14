package com.cognitionbox.petra.examples.mas.pedestriancrossing;


import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;
import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

// --- MAIN ENTRY POINT ---
public class Main {
    public static void main(String[] args) throws InterruptedException {
        startReactive(new TrafficControlMAS(), 3000, singleton(PedestrianAgent.class), singleton(TrafficAgent.class));
    }
}








package com.cognitionbox.petra.examples.mas.researchlab;

import static com.cognitionbox.petra.ast.interp.util.Program.startReactive;
import static com.cognitionbox.petra.ast.interp.util.Singleton.singleton;

// --- MAIN ENTRY POINT ---
public class Main {
    public static void main(String[] args) throws InterruptedException {
        startReactive(new ReleaseGateMAS(), 3000, singleton(LabAgent.class), singleton(OwnerAgent.class));
    }
}

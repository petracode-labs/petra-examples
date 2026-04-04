package com.cognitionbox.petra.examples.mas.pedestriancrossing;

import static com.cognitionbox.petra.examples.trading.strategy.data.Singleton.get;

// --- MAIN ENTRY POINT ---
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TrafficControlMAS mas = new TrafficControlMAS();
        while(true){
            get(PedestrianAgent.class).act();
            get(TrafficAgent.class).act();
            mas.act();
            Thread.sleep(1000);
        }
    }
}








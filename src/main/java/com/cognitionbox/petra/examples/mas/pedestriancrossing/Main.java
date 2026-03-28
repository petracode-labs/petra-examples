package com.cognitionbox.petra.examples.mas.pedestriancrossing;

// --- MAIN ENTRY POINT ---
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TrafficControlMAS mas = new TrafficControlMAS();
        while (true) {
            mas.act();
            Thread.sleep(1000);
        }
    }
}








 package com.cognitionbox.petra.examples.trailingstoploss;

 import com.cognitionbox.petra.ast.terms.External;

 @External
 public class Main {
     public static void main(String[] args) {
         TrailingStopLoss trailingStopLoss = new TrailingStopLoss();
         trailingStopLoss.update();
         trailingStopLoss.update();
         trailingStopLoss.update();
         // ...
     }
 }
package com.cognitionbox.petra.examples.base.algorithms;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public class BaseAlgorithms {

    public static int factorial(int n) {
        if (n>=0 && n<17){
            if (n <= 0) return 1;
            int result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
                assert result > 0 : "Result must remain strictly positive";
            }
            return result;
        }
        return -1; // unknown
    }

    public static int fibonacci(int n) {
        if (n>=0 && n<47){
            if (n <= 1) return n;
            int a = 0, b = 1;
            for (int i = 2; i <= n; i++) {
                int temp = a + b;
                assert temp >= b : "Fibonacci sequence must be non-decreasing";
                a = b;
                b = temp;
            }
            assert b > 0 : "Final Fibonacci number for n > 1 must be positive";
            return b;
        }
        return -1; // unknown
    }

    public static int gcd(int a, int b) {
        if (a>0 && a<100 && b>0 && b<100){
            assert a > 0 && b > 0 : "Initial inputs must be positive";
            while (b != 0) {
                assert b > 0 : "Divisor must remain positive during evaluation";
                int temp = b;
                b = a % b;
                a = temp;
            }
            assert a > 0 : "GCD result must be positive";
            return a;
        }
        return -1; // unknown
    }

    // TODO: needs looking into
//    public static int isPrime(int n) {
//        if (n>0 && n<100){
//            if (n <= 1) return 0; // false
//            for (int i = 2; i * i <= n; i++) {
//                if (n % i == 0) return 0; // false
//                assert n % i != 0 : "Current divisor does not divide n";
//            }
//            assert n > 1 : "Identified primes are strictly greater than 1";
//            return 1; // true
//        }
//        return -1; // unknown
//    }

}
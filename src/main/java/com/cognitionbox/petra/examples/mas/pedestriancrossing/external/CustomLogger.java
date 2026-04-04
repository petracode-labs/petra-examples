package com.cognitionbox.petra.examples.mas.pedestriancrossing.external;

import com.cognitionbox.petra.ast.terms.External;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@External
public final class CustomLogger {

    public static void log(String level, String message) {
        // Get current date-time in the local time zone
        ZonedDateTime now = ZonedDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z"));

        // Get the current thread name
        String threadName = Thread.currentThread().getName();

        // Get the calling class and method
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            StackTraceElement caller = stackTrace[2];
            String[] split = caller.getClassName().split("\\.");
            String className = "*";//split[split.length-1];
            String methodName = caller.getMethodName();

            String trackedTimestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            // Format the log message
            String formattedLog = String.format("%s [%s] %s %s %-5s %s.%s - %s",
                    timestamp, threadName, 0, trackedTimestamp, level, className, methodName, message);

            // Print to System.out
            System.out.println(formattedLog);
        }
    }

    public static void log(String message) {
        // Get current date-time in the local time zone
        ZonedDateTime now = ZonedDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z"));

        // Get the current thread name
        String threadName = Thread.currentThread().getName();

        // Get the calling class and method
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            StackTraceElement caller = stackTrace[4];
            String[] split = caller.getClassName().split("\\.");
            String className = "*";//split[split.length-1];
            String methodName = caller.getMethodName();

            String trackedTimestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            // Format the log message
            String formattedLog = String.format("%s [%s] %s %s %-5s %s.%s - %s",
                    timestamp, threadName, 0, trackedTimestamp, "DEBUG", className, methodName, message);

            // Print to System.out
            System.out.println(formattedLog);
        }
    }
}

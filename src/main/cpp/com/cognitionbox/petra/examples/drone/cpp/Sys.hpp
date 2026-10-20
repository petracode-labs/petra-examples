#pragma once
#include <iostream>
#include <cstdlib>
#include <chrono>
#include <thread>

[[external]]
class Sys final {
public:
    void exit() {
        std::exit(0);
    }

    void logTurnOn() {
        std::cout << "turnOn" << std::endl;
    }

    void logTurnOff() {
        std::cout << "turnOff" << std::endl;
    }

    void logFlyHome() {
        std::cout << "logFlyHome" << std::endl;
    }

    void logLand() {
        std::cout << "logLand" << std::endl;
    }

    void logRouteActive() {
        std::cout << "logRouteActive" << std::endl;
    }

    void logWaitUntilHome() {
        std::cout << "waitUntilHome" << std::endl;
    }

    void sleep() {
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
    }

    void logWaitUntilLanded() {
        std::cout << "waitUntilLanded" << std::endl;
    }

    void logWaitUntilTakenOff() {
        std::cout << "waitUntilTakenOff" << std::endl;
    }

    void logTravelToHome() {
        std::cout << "travelToHome" << std::endl;
    }

    void logWaitUntilC() {
        std::cout << "waitUntilC" << std::endl;
    }

    void logWaitUntilB() {
        std::cout << "waitUntilB" << std::endl;
    }

    void logWaitUntilA() {
        std::cout << "waitUntilA" << std::endl;
    }

    void logTravelFromGroundToTakeOff() {
        std::cout << "travelFromGroundToTakeOff" << std::endl;
    }

    void logTravelFromGroundToA() {
        std::cout << "travelFromGroundToA" << std::endl;
    }

    void logTravelFromAToB() {
        std::cout << "travelFromAToB" << std::endl;
    }

    void logTravelFromBToC() {
        std::cout << "travelFromBToC" << std::endl;
    }

    void logTravelToLand() {
        std::cout << "travelToLand" << std::endl;
    }

    void logTemperatureWarning() {
        std::cout << "temperatureWarning" << std::endl;
    }
};
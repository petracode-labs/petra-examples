#pragma once
#include <cassert>
#include <cstdlib>
#include <atomic>
#include "Sys.hpp"
#include "Waypoint.hpp"
#include "Program.hpp"

[[external]]
class DroneConnection final : public Runnable {
private:
    Sys sys;
    Waypoint home{0, 0, 0};
    Waypoint takeOff{0, 0, 100};
    Waypoint a{10, 0, 100};
    Waypoint b{10, 10, 100};
    Waypoint c{20, 20, 100};

    std::atomic<double> rc{0.0};
    std::atomic<int> temp{0};
    std::atomic<double> battery{100.0};
    std::atomic<double> snr{1.0};

    std::atomic<int> x{0};
    std::atomic<int> y{0};
    std::atomic<int> z{0};

    DroneConnection() = default;

    void readRc() {
        rc.store(static_cast<double>(std::rand()) / RAND_MAX);
    }

    void readTemp() {
        temp.store(std::rand() % 100);
    }

    void readBattery() {
        battery.store(std::rand() % 100);
    }

    void readSNR() {
        snr.store(static_cast<double>(std::rand()) / RAND_MAX);
    }

public:
    static DroneConnection& getDroneConnection() {
        static DroneConnection instance;
        return instance;
    }

    bool low() const { return temp.load() < 30; }
    bool normal() const { return temp.load() >= 30 && temp.load() <= 70; }
    bool high() const { return temp.load() > 70; }

    bool okLevel() const { return battery.load() >= 50.0; }
    bool returnHomeLevel() const { return battery.load() < 50.0; }

    bool right() const { return rc.load() >= 0.0 && rc.load() < 0.25; }
    bool left() const { return rc.load() >= 0.25 && rc.load() < 0.5; }
    bool forward() const { return rc.load() >= 0.5 && rc.load() < 0.75; }
    bool back() const { return rc.load() >= 0.75 && rc.load() < 1.0; }

    int getX() const { return x.load(); }
    int getY() const { return y.load(); }
    int getZ() const { return z.load(); }

    bool atA() const { return getX() == a.getX() && getY() == a.getY() && getZ() == a.getZ(); }
    bool atB() const { return getX() == b.getX() && getY() == b.getY() && getZ() == b.getZ(); }
    bool atC() const { return getX() == c.getX() && getY() == c.getY() && getZ() == c.getZ(); }

    int getVelocityX() const { return 0; }
    int getVelocityY() const { return 0; }
    int getVelocityZ() const { return 0; }

    int getPower() const { return 0; }
    int getTemp() const { return std::rand() % 100; }
    float getSNR() const { return 0.0f; }

    void goToXYZ(int newX, int newY, int newZ) {
        x.store(newX);
        y.store(newY);
        z.store(newZ);
    }

    void goTo(const Waypoint& waypoint) {
        goToXYZ(waypoint.getX(), waypoint.getY(), waypoint.getZ());
    }

    void land() {}

    bool lowSNR() const { return snr.load() < 0.2; }
    bool normalSNR() const { return snr.load() >= 0.2 && snr.load() <= 0.6; }
    bool highSNR() const { return snr.load() > 0.6; }

    void run() override {
        readSNR();
        readTemp();
        readBattery();
        readRc();
    }

    void goToTakeoff() { goTo(takeOff); }

    void goToHome() {
        sys.logTravelToHome();
        goTo(home);
    }

    void goToA() {
        sys.logTravelFromGroundToA();
        goTo(a);
    }

    void goToB() {
        sys.logTravelFromAToB();
        goTo(b);
    }

    void goToC() {
        sys.logTravelFromBToC();
        goTo(c);
    }

    void goToLand() {
        sys.logTravelToLand();
        Waypoint waypoint{getX(), getY(), 0};
        goToXYZ(waypoint.getX(), waypoint.getY(), waypoint.getZ());
    }

    bool onLand() const { return getX() != 0 && getY() != 0 && getZ() == 0; }
    bool atHome() const { return getX() == 0 && getY() == 0 && getZ() == 0; }

    void waitUntilHome() {
        sys.logWaitUntilHome();
        while (!atHome()) {
            sys.sleep();
        }
        assert(atHome());
    }

    void waitUntilLanded() {
        sys.logWaitUntilLanded();
        while (!onLand()) {
            sys.sleep();
        }
        assert(onLand());
    }

    void waitUntilA() {
        sys.logWaitUntilA();
        while (!atA()) {
            sys.sleep();
        }
        assert(atA());
    }

    void waitUntilB() {
        sys.logWaitUntilB();
        while (!atB()) {
            sys.sleep();
        }
        assert(atB());
    }

    void waitUntilC() {
        sys.logWaitUntilC();
        while (!atC()) {
            sys.sleep();
        }
        assert(atC());
    }
};
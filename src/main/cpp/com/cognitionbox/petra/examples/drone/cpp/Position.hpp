#pragma once
#include <cassert>
#include "DroneConnection.hpp"

class Position final {
private:
    DroneConnection& connection = DroneConnection::getDroneConnection();

public:
    bool a() const { return connection.atA(); }
    bool b() const { return connection.atB(); }
    bool c() const { return connection.atC(); }
    bool onLand() const { return connection.onLand(); }
    bool atHome() const { return connection.atHome(); }

    void travelToHome() {
        if (onLand() ^ atHome() ^ a() ^ b() ^ c()) {
            connection.goToHome();
            connection.waitUntilHome();
            assert(atHome());
        }
    }

    void travelToLand() {
        if (onLand() ^ atHome() ^ a() ^ b() ^ c()) {
            connection.goToLand();
            connection.waitUntilLanded();
            assert(onLand());
        }
    }

    void travelFromGroundToA() {
        if (onLand() ^ atHome() ^ a() ^ b() ^ c()) {
            connection.goToA();
            connection.waitUntilA();
            assert(a());
        }
    }

    void travelFromAToB() {
        if (onLand() ^ atHome() ^ a() ^ b() ^ c()) {
            connection.goToB();
            connection.waitUntilB();
            assert(b());
        }
    }

    void travelFromBToC() {
        if (onLand() ^ atHome() ^ a() ^ b() ^ c()) {
            connection.goToC();
            connection.waitUntilC();
            assert(c());
        }
    }
};
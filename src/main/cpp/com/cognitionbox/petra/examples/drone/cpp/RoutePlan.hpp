#pragma once
#include <cassert>
#include "Position.hpp"

class RoutePlan final {
private:
    Position position;

public:
    bool atHome() const { return position.atHome(); }
    bool a() const { return position.a(); }
    bool b() const { return position.b(); }
    bool c() const { return position.c(); }
    bool ground() const { return position.onLand(); }

    void travel() {
        if (atHome() ^ ground()) {
            position.travelFromGroundToA();
            assert(a());
        } else if (a()) {
            position.travelFromAToB();
            assert(b());
        } else if (b()) {
            position.travelFromBToC();
            assert(c());
        } else if (c()) {
            position.travelToHome();
            assert(atHome());
        }
    }

    void land() {
        if (atHome() ^ ground() ^ a() ^ b() ^ c()) {
            position.travelToLand();
            assert(ground());
        }
    }

    void returnToHome() {
        if (atHome() ^ ground() ^ a() ^ b() ^ c()) {
            position.travelToHome();
            assert(atHome());
        }
    }
};
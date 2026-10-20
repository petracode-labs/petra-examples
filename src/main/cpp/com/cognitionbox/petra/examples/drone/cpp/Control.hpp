#pragma once
#include <cassert>
#include "SysWrapper.hpp"
#include "Flag.hpp"

class Control final {
private:
    SysWrapper sys;
    Flag flag;

public:
    [[initial]]
    bool on() const {
        return flag.on() && sys.ok();
    }

    bool off() const {
        return flag.off() && sys.ok();
    }

    void turnOn() {
        if (on() ^ off()) {
            sep([this]() { flag.turnOn(); },
                [this]() { sys.logTurnOn(); });
            assert(on());
        }
    }

    void turnOff() {
        if (on()) {
            sep([this]() { flag.turnOff(); },
                [this]() { sys.logTurnOff(); });
            assert(off());
        }
    }

    void exit() {
        if (off()) {
            sys.exit();
            assert(off());
        }
    }
};
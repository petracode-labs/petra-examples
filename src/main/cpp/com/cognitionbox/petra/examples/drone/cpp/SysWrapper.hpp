#pragma once
#include <cassert>
#include "Sys.hpp"

[[base]]
class SysWrapper final {
private:
    Sys sys;

public:
    [[initial]]
    bool ok() const { return true; }

    void exit() {
        if (ok()) {
            sys.exit();
            assert(ok());
        }
    }

    void logTurnOn() {
        if (ok()) {
            sys.logTurnOn();
            assert(ok());
        }
    }

    void logTurnOff() {
        if (ok()) {
            sys.logTurnOff();
            assert(ok());
        }
    }

    void logLand() {
        if (ok()) {
            sys.logLand();
            assert(ok());
        }
    }

    void logRouteActive() {
        if (ok()) {
            sys.logRouteActive();
            assert(ok());
        }
    }

    void logTemperatureWarning() {
        if (ok()) {
            sys.logTemperatureWarning();
            assert(ok());
        }
    }
};
#pragma once
#include <cassert>
#include "SysWrapper.hpp"
#include "RoutePlan.hpp"
#include "Diagnostics.hpp"
#include "Control.hpp"
#include "Program.hpp"

class Controller final : public Runnable {
private:
    SysWrapper sys;
    RoutePlan routePlan;
    Diagnostics diagnostics;
    Control control;

public:
    [[initial]]
    bool routeActive() const {
        return control.on() && diagnostics.ok();
    }

    bool flyHome() const {
        return control.on() && diagnostics.flyHomeImmediately();
    }

    bool grounded() const {
        return control.off();
    }

    bool temperatureWarning() const {
        return control.on() && diagnostics.temperatureWarning();
    }

    void run() override {
        if (grounded()) {
            sys.exit();
            assert(grounded());
        } else if (flyHome()) {
            sep([this]() { sys.logLand(); },
                [this]() { routePlan.returnToHome(); },
                [this]() { control.turnOff(); });
            assert(grounded());
        } else if (routeActive()) {
            sep([this]() { sys.logRouteActive(); },
                [this]() { routePlan.travel(); });
            assert(routeActive());
        } else if (temperatureWarning()) {
            sys.logTemperatureWarning();
            assert(temperatureWarning());
        }
    }
};
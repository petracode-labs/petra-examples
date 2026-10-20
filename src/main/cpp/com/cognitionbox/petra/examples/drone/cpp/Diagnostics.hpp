#pragma once
#include "TemperatureTrigger.hpp"
#include "FlyHomeTrigger.hpp"

class Diagnostics final {
private:
    TemperatureTrigger temperatureTrigger;
    FlyHomeTrigger flyHomeTrigger;

public:
    [[initial]]
    bool ok() const {
        return temperatureTrigger.disabled() && flyHomeTrigger.disabled();
    }

    bool temperatureWarning() const {
        return temperatureTrigger.enabled();
    }

    bool flyHomeImmediately() const {
        return temperatureTrigger.disabled() && flyHomeTrigger.enabled();
    }
};
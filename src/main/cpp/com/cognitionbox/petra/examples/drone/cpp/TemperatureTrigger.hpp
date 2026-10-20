#pragma once
#include "Temperature.hpp"

class TemperatureTrigger final {
private:
    Temperature temperature;

public:
    bool enabled() const {
        return temperature.high();
    }

    [[initial]]
    bool disabled() const {
        return !temperature.high();
    }
};
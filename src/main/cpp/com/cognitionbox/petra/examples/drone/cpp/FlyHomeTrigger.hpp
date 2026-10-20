#pragma once
#include "Wifi.hpp"
#include "Battery.hpp"

class FlyHomeTrigger final {
private:
    Wifi wifi;
    Battery battery;

public:
    bool enabled() const {
        return wifi.lowSNR() || battery.returnHomeLevel();
    }

    [[initial]]
    bool disabled() const {
        return !(wifi.lowSNR() || battery.returnHomeLevel());
    }
};
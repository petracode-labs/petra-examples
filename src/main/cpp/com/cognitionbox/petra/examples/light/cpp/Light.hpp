#include <cassert>
// Include dependencies so the compiler knows what Power and Control are
#include "Power.hpp"
#include "Control.hpp"

// Define sep so the compiler can validate the syntax
template <typename F1, typename F2>
void sep(F1&& f1, F2&& f2) {
    f1();
    f2();
}

class Light final {
private:
    Power power;
    Control control;

public:
    [[initial]]
    bool off() const {
        return power.off() || control.off();
    }

    bool on() const {
        return power.on() && control.on();
    }

    // Removed 'override' as Light does not inherit from a base class here
    void run() {
        if (off()) {
            sep([this]() { power.turnOn(); },
                [this]() { control.turnOn(); });
            assert(on());
        } else if (on()) {
            sep([this]() { power.turnOff(); },
                [this]() { control.turnOff(); });
            assert(off());
        }
    }
};
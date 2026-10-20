#include <cassert>

[[base]]
class Flag final {
private:
    bool bool_ = true;

public:

    [[initial]]
    bool on() const {
        return bool_ == true;
    }

    bool off() const {
        return bool_ == false;
    }

    void turnOn() {
        if (on() || off()) {
            bool_ = true;
            assert(on());
        }
    }

    void turnOff() {
        if (on() || off()) {
            bool_ = false;
            assert(off());
        }
    }
};
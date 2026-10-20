#include <cassert>

[[base]]
class Power final {
private:
    bool bool_ = false;

public:
    bool on() const {
        return bool_ == true;
    }

    [[initial]]
    bool off() const {
        return bool_ == false;
    }

    void turnOn() {
        if (on() ^ off()) {
            bool_ = true;
            assert(on());
        }
    }

    void turnOff() {
        if (on() ^ off()) {
            bool_ = false;
            assert(off());
        }
    }
};
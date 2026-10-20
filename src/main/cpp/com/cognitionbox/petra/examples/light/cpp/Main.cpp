#include <iostream>
#include "Light.hpp"

int main() {
    Light light;

    std::cout << "Initial state: " << (light.on() ? "ON" : "OFF") << '\n';

    // Call 1: Transitions from OFF to ON
    light.run();
    std::cout << "Call 1 state:  " << (light.on() ? "ON" : "OFF") << '\n';

    // Call 2: Transitions from ON to OFF
    light.run();
    std::cout << "Call 2 state:  " << (light.on() ? "ON" : "OFF") << '\n';

    // Call 3: Transitions from OFF to ON
    light.run();
    std::cout << "Call 3 state:  " << (light.on() ? "ON" : "OFF") << '\n';

    // Call 4: Transitions from ON to OFF
    light.run();
    std::cout << "Call 4 state:  " << (light.on() ? "ON" : "OFF") << '\n';

    return 0;
}
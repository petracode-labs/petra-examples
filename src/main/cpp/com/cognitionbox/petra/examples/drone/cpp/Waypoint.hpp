#pragma once

[[external]]
class Waypoint final {
private:
    int x = 0;
    int y = 0;
    int z = 0;

public:
    Waypoint() = default;
    Waypoint(int x, int y, int z) : x(x), y(y), z(z) {}

    int getX() const { return x; }
    int getY() const { return y; }
    int getZ() const { return z; }
};
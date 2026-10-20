#pragma once
#include "DroneConnection.hpp"

[[base]]
class Temperature final {
private:
    DroneConnection& connection = DroneConnection::getDroneConnection();

public:
    [[initial]]
    bool low() const { return connection.low(); }
    bool normal() const { return connection.normal(); }
    bool high() const { return connection.high(); }
};
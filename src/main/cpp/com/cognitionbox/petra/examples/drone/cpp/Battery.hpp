#pragma once
#include "DroneConnection.hpp"

class Battery final {
private:
    DroneConnection& connection = DroneConnection::getDroneConnection();

public:
    bool returnHomeLevel() const {
        return connection.returnHomeLevel();
    }

    [[initial]]
    bool okLevel() const {
        return connection.okLevel();
    }
};
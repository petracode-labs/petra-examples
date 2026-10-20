#pragma once
#include "DroneConnection.hpp"

[[base]]
class Wifi final {
private:
    DroneConnection& connection = DroneConnection::getDroneConnection();

public:
    bool lowSNR() const { return connection.lowSNR(); }
    bool normalSNR() const { return connection.normalSNR(); }

    [[initial]]
    bool highSNR() const { return connection.highSNR(); }
};
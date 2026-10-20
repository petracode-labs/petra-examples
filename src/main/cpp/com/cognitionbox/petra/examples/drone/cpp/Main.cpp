#include "Controller.hpp"
#include "DroneConnection.hpp"
#include "Program.hpp"

int main(int argc, char* argv[]) {
    Program::startReactive(0, Controller{}, DroneConnection::getDroneConnection());
    return 0;
}
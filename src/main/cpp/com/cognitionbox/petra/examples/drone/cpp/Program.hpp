#pragma once

#include <chrono>
#include <cstdint>
#include <limits>
#include <thread>
#include <vector>

struct Runnable {
    virtual ~Runnable() = default;
    virtual void run() = 0;
    void operator()() { run(); }
};

struct Reactive {
    virtual ~Reactive() = default;
    virtual bool updateable() = 0;
    virtual void update() = 0;
    virtual void main() = 0;
};

struct EntryPoint {
    virtual ~EntryPoint() = default;
    virtual void main() = 0;
};

struct Updateable {
    virtual ~Updateable() = default;
    virtual bool updateable() = 0;
    virtual void update() = 0;
};

class Program final {
private:
    inline static int count = 0;

    static void sleep_ms(long long ms) {
        if (ms > 0) std::this_thread::sleep_for(std::chrono::milliseconds(ms));
    }

public:
    Program() = delete;

    template <typename... Fs>
    static void seq(Fs&&... fs) {
        (fs(), ...);
    }

    template <typename... Fs>
    static void par(Fs&&... fs) {
        if constexpr (sizeof...(Fs) <= 1) {
            (fs(), ...);
        } else {
            std::vector<std::thread> threads;
            (threads.emplace_back(fs), ...);
            for (auto& t : threads) {
                if (t.joinable()) t.join();
            }
        }
    }

    template <typename... Fs>
    static void sep(Fs&&... fs) {
        par(fs...);
    }

    template <typename F>
    static void start(F&& entryPoint) {
        entryPoint();
    }

    template <typename Entry, typename... Rest>
    static void startReactive(long long iterations, long long pauseMs, Entry&& entry, Rest&&... rest) {
        while (count < iterations) {
            entry();
            (rest(), ...);
            sleep_ms(pauseMs);
            count++;
        }
    }

    template <typename Entry, typename... Rest>
    static void startReactive(long long pauseMs, Entry&& entry, Rest&&... rest) {
        startReactive(std::numeric_limits<long long>::max(), pauseMs, entry, rest...);
    }

    static void startReactive(Reactive& program, long long pauseMs = 0) {
        while (program.updateable()) {
            program.update();
            program.main();
            sleep_ms(pauseMs);
            count++;
        }
    }

    template <typename... Updateables>
    static void startReactive(EntryPoint& program, long long pauseMs, Updateables&... updateables) {
        while ((updateables.updateable() && ...)) {
            (updateables.update(), ...);
            program.main();
            sleep_ms(pauseMs);
            count++;
        }
    }

    template <typename... Updateables>
    static void startReactive(EntryPoint& program, Updateables&... updateables) {
        startReactive(program, 0, updateables...);
    }

    static void resetCount() { count = 0; }
    static int getCount() { return count; }
};

template <typename... Fs>
void sep(Fs&&... fs) { Program::sep(fs...); }

template <typename... Fs>
void seq(Fs&&... fs) { Program::seq(fs...); }

template <typename... Fs>
void par(Fs&&... fs) { Program::par(fs...); }
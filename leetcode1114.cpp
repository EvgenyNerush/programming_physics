#include <mutex>
#include <condition_variable>

class Foo {
public:
    // mutex ensures that wait() in condition variable
    // causes no race condition
    std::mutex m;
    std::condition_variable cv;
    int thread_now;
    Foo() {
        thread_now = 1;
    }

    void first(function<void()> printFirst) {
        // printFirst() outputs "first". Do not change or remove this line.
        printFirst();
        thread_now = 2;
        cv.notify_all(); // wakes up all the waiting threads
    }

    void second(function<void()> printSecond) {
        auto lk = std::unique_lock(m);
        // block the thread until `cv` is notified and condition met;
        // during wait() `m` is locked
        cv.wait(lk, [this](){return thread_now == 2;});
        // printSecond() outputs "second". Do not change or remove this line.
        printSecond();
        thread_now = 3;
        cv.notify_all();
    }

    void third(function<void()> printThird) {
        auto lk = std::unique_lock(m);
        cv.wait(lk, [this](){return thread_now == 3;});
        // printThird() outputs "third". Do not change or remove this line.
        printThird();
    }
};

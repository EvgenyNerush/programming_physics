// -std=c++11
#include <iostream>
#include <list>
#include <cstdlib>
#include <chrono>
using namespace std;

// sum of elements (aka `fold` function)
template<typename D> D sum(const list<D>& data, D res0) {
    D res = res0;
    for (auto x: data) { // const iterator
        res += x;
    }
    return res;
}

int main() {
    // let's create a list with random numbers
    size_t n = 300000;
    list<double> xs;
    srand(123);
    for (long i = 0; i < n; ++i) {
        xs.push_back(static_cast<double>(rand()) / RAND_MAX);
    }

    // now measure the sum time
    auto t0 = chrono::high_resolution_clock::now();
    double s = sum(xs, 0.0);
    auto t1 = chrono::high_resolution_clock::now();

    cout << "sum of unsorted list = " << s << "\n"; // approximately n / 2
    double t = 1e-9 * chrono::duration_cast<chrono::nanoseconds>(t1 - t0).count();
    cout << "sum of unsorted takes " << t << " s\n";
    cout << "time per element = " << t / n << " s\n";

    // SORTING
    xs.sort();

    // now measure the same sum time...
    t0 = chrono::high_resolution_clock::now();
    s = sum(xs, 0.0);
    t1 = chrono::high_resolution_clock::now();

    cout << "sum of sorted list = " << s << "\n"; // obviously, should be the same
    t = 1e-9 * chrono::duration_cast<chrono::nanoseconds>(t1 - t0).count();
    cout << "sum of sorted takes = " << t << " s\n";
    cout << "time per element = " << t / n << " s\n"; // \O_O/
    return 0;
}


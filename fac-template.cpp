#include <iostream>

template <long long int n>
long long int fac() {
    return n * fac<n - 1>();
}

template <>
long long int fac<1>() {
    return 1;
}

int main() {
    std::cout << fac<2>() << '\n' // 2
              << fac<3>() << '\n' // 6
              << fac<4>() << '\n' // 24
              << fac<5>() << '\n' // 120
              ;
    return 0;
}

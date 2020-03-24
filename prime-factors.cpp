// Dijkstra, p. 187
#include <iostream>
using namespace std;

const long long N = 1524157877488187881; // 1234567891^2

int main() {
    long long p; // that we are looking for
    long long f = 2;
    while ( N % f != 0 &&  (f + 1) * (f + 1) <= N) {
        f += 1;
    }
    if (N % f != 0) {
        p = N;
    } else {
        p = f;
    }
    cout << N << "\n" << f << "\n";
    return 0;
}

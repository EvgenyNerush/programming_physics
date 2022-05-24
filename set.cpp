#include <iostream>
#include <iterator>
#include <set>
  
using namespace std;
  
int main()
{
    set<int> s = {4, 2, 3, 6, 1, 7};
    s.insert(5);
    s.insert(5); // possible but `s` is the same
  
    set<int>::iterator it;
    for (it = s.begin(); it != s.end(); it++) {
        cout << *it << ", ";
    }
    cout << endl;
    // 1, 2, 3, 4, 5, 6, 7, 
  
    it = s.find(2);
    if (it != s.end()) {
        s.erase(it);
    }

    for (auto x: s) {
        cout << x << ", ";
    }
    // 1, 3, 4, 5, 6, 7, 

    return 0;
}

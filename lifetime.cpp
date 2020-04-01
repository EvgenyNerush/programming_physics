#include <iostream>

using namespace std;

const int a = 1;
      int b = a + 1;

void f() {
    b = b + 1;
}

int main(){
    int* pc; // pointer to c
    {
        int c = 3; // what changes if this variable
                   // is static int?
        pc = &c;
    }
    int   d = 4;
    int* pd = &d;
    cout << *pc << '\n'
         <<  pc << '\n'
         <<  pd << '\n'
         ;
    return 0;
}

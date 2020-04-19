#include <iostream>

template <typename A, typename B> struct pair {
  A a;
  B b;
};

// обозначим эту функцию "1"
int oops(int a){
  return 1;
}

// шаблон "2"
template<typename A> int oops(A a){
  return 10 + oops(a); // это может быть вызов любой из трёх функций!
}

// шаблон "3"
template <typename A, typename B> int oops(pair<A, B> p) {
  return 100
       + oops   (p.a)  // нет <>
       + oops<B>(p.b); // а здесь <B>
}

int main()
{
  pair< pair<int, int>, pair<int, int> >    p;
  pair< pair<int, int>, pair<int, double> > r;
  std::cout << oops(p) << '\n'; // 334 (!)
  //std::cout << oops(r) << '\n'; // OOPS!
}

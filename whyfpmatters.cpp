#include <iostream>

template <typename T> class List {
public:
    T* head; // pointer to value
    List<T>* tail;
    // head == nullptr -> пустой список
    // tail == nullptr -> список из одного элемента
    //
    // императивный код, отложенные вычисления
    // написать сложнее; работаем в куче
    List<T>() {
        head = nullptr;
        tail = nullptr;
    }
    void push_front(T x) {
        if (head == nullptr) {
            head = new T;
            *head = x;
        } else {
            List<T>* p = new List<T>;
            // копируем текущий элемент
            p->head = head;
            p->tail = tail;
            // в текущий кладём новое значение
            head = new T;
            *head = x;
            tail = p;
        }
        std::cout << x << "\t" << head << "\t" << tail << "\n";
    }
    void unsafe_pop_front() {
        std::cout << *head << "\t" << head << "\t" << tail << "\n";
        if (tail == nullptr) {
            std::cout << head << "\tupop\n";
            delete head;
            head = nullptr;
            std::cout << head << "\tupop2\n";
        } else {
            T* tmp_head = head;
            List<T>* tmp_tail = tail;
            head = tail->head;
            tail = tail->tail;
            delete tmp_head;
            delete tmp_tail;
        }
    }
    void pop_front() {
        if (head != nullptr) {
            unsafe_pop_front();
        }
    }
    ~List<T>() {
        while (head != nullptr) {
            std::cout << head << "\tdestr\n";
            unsafe_pop_front();
        }
    }
};

// передаём функцию в качестве аргумента
template <typename T, typename U> U foldl(U f(U, T), U x0, List<T> xs) {
    if (xs.head == nullptr) {
        return x0;
    } else {
        U x1 = f(x0, *(xs.head));
        xs.pop_front();
        return foldl(f, x1, xs);
    }
}

int plus(int x, int y) {
    return x + y;
}

int main() {
    List<int> mylist;
    mylist.push_front(0);
    mylist.push_front(1);
    mylist.push_front(2);
    std::cout << "QWE\n";
    std::cout << foldl<int,int>(plus, 0, mylist) << "\n";
    // lambda functions (closures)
    // [capture](params){body}
    std::cout << foldl<int,int>([](int x, int y){return x + y;}, 0, mylist) << "\n";
    std::cout << foldl<int,int>([](int x, int y){return x * y;}, 0, mylist) << "\n";
    return 0;
}

// json.cpp

#include <iostream>
using namespace std;

// We will not write constructors and destructors for all classes here,
// but will write a serialization method - toString

// Base class - key-value pairs
class JNode {
    public:
        string key; // For key-value. What about value?
        virtual string toString() = 0; // serialization, should be
                                       // provided by child classes
};

// JInt inherits (extends) JNode
class JInt: public JNode {
    public:
        int value;
        JInt(string, int); // constructor
        string toString();
};

class JString: public JNode {
    public:
        string value;
        JString(string, string);
        string toString();
};

// JSON is just a list of pointers to JNodes. Note that
// JNode can contain another JSON in itself - see JObj!
class JSON {
    public:
        int n; // list length
        JNode** list;
        string toString();
};

// JSON object, with name (or `key`)
class JObj: public JNode {
    public:
        JSON value;
        string toString();
};

// now its time to define constructors...

JInt::JInt(string s, int i) {
    key = s;
    value = i;
}

JString::JString(string key1, string value1) {
    key = key1;
    value = value1;
}

// ...and serialization

string JInt::toString() {
    return "\"" + key + "\": " + to_string(value);
}

string JString::toString() {
    // the code is slightly different from that for JInt
    return "\"" + key + "\": \"" + value + "\"";
}

string JObj::toString() {
    return "\"" + key + "\": " + value.toString();
}

// Here we go along the list which contains
// objects of different types!
string JSON::toString() {
    string s = "{ ";
    for (int i = 0; i < n - 1; ++i) {
        JNode* p = list[i];
        s += p->toString() + ", ";
    }
    s += list[n-1]->toString() + " }";
    return s;
}

int main() {

    JString name("name", "Douglas");
    JInt age("age", 70);

    /* {
     *     "name": "Douglas",
     *     "age": 70
     * }
     */
    JSON j;
    j.n = 2;
    j.list = new JNode*[j.n];
    j.list[0] = &name;
    j.list[1] = &age;

    JObj person;
    person.key = "CEO";
    person.value = j;

    JSON main;
    main.n = 2;
    main.list = new JNode*[main.n];
    JString city("city", "Kyiv");
    main.list[0] = &city;
    main.list[1] = &person;

    cout << main.toString() << '\n';
    // The result is
    // { "city": "Kyiv", "CEO": { "name": "Douglas", "age": 70 } }

    // delete...

    return 0;
}

# For records (structs) use maps. Instead of C++ struct
# class Electrons {
#     double charge = -1;
#     double mass = 1;
#     vector<double> xs; // coordinates
#     vector<double> pxs; // momentums
# };
# we have just a dictionary. It's very pythonic!
electrons = {'charge': -1, 'mass': 1, 'xs': [], 'pxs': []}

# Another example: for enumerations, like in C++
# enum color { red, yellow, green, blue };
# enum roman { I, V, X, L, C, D, M }; // then "+", to_string etc should be introduced
romans = {'I': 1, 'V': 5, 'X': 10, 'L': 50, 'C': 100, 'D': 500, 'M': 1000}

# also useful for match cases, like pattern matching in Scala:
# def singleRomanToInt(c: Char) = c match {
#   case 'I' => 1
#   case 'V' => 5
#   ...
def single_roman_to_int(c):
    return romans[c]

print(single_roman_to_int('V')) # 5


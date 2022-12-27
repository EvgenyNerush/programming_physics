# Typical functions...
def f(x):
    return 1/(x - 1)

#print(f(f(1))) # => exception

try:
    y = f(f(1)) # secuence of calls
except:
    None

# monad-like
def fm(x):
    if x == 0: # we do not check here if x == None!
        return None
    else:
        return 1/x

def flatMap(x, h):
    if x == None: # we check here whatether x is None
        return None
    else:
        return h(x)

#### chain of calls that can fail ####
print(flatMap(fm(1), fm)) # None

# another example
print(flatMap(fm(2), fm)) # 2.0

# flatten: see `dropna` in pandas.
# flatten for list of lists:
xs = [[1,2], [3,4]]
# list comprehension is exactly the monadic interface for lists!
ys = [y for x in xs for y in x] # [1,2,3,4]
print(ys)

# one more example
print( [y for x in [1,2,3] for y in [x, -x]] ) # [1,-1,2,-1,3,-3]

print( [y for x in ['Ab', 'Cd'] for y in x.lower()] ) # ['a',b','c','d']

import string
letters = string.ascii_letters[:26]
print( [a + b for a in letters for b in letters if a!=b] ) # ['ab','ac',...,'zx','zy']

# Option? None? Exception... :(
# Dictionaries? :(


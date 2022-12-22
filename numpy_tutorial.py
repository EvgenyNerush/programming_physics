import numpy as np

a = np.array([1, 2, 3, 4, 5, 6])
print(type(a)) # numpy.ndarray
# data type, to tell how it should be stored
x = np.array([1,2,3], dtype=np.float) # int, int64, float, float64...

# another way - np.fromfile (ok for binary also)

print(a.shape) # (6,)
b = np.reshape(a, (2,3))
# [[1,2,3],
#  [4,5,6]]
print(b[0,1]) # 2; доступ к элементу по индексу, b[0,1] = 42 is ok
# при этом хранимые в памяти числа не переставляются скорее всего, меняется только
# lookup-функция.
print(b.flatten()) # to one-dim array; or reshape(-1), no need to know size

# create empty, ones, random, zeros...
# zeros_like(b), ones_like...
# arange, linspace...
#np.empty((5, 4))
#np.eye(3) # identity matrix
#np.random.random((2,2)) # uniform (linear distribution), [0, 1)
#np.random.random(5) # 5 random numbers

print(b.transpose()) # also sort, concatenate, hstack, vstack, mean, std (standard deviation)...

# slicing work as for lists
c = b[:,1:] # 0-ую колонку не выводим
print(c)
# [[2, 3]
#  [5, 6]]

# Map, reduce, filter - enough for almost everything.

# Map-like, векторная операция, поэлементно:
print(np.sqrt(b))
print(np.sin(b))
x = 0.1 * x
y = np.sin(x) + np.cos(x) # ...plot(x, y)
y = x * y # поэлементно
def f(x):
    return np.sin(x) + np.sqrt(x)
print(f(b)) # ok for numpy arrays and numbers too!
# Для функций, содержащих if, можно использовать g = np.vectorize(f), then g([1,2,3,4]).
# В питоне также есть map, возвращающая генератор (да!),
# проще пользоваться list comprehenshion

# Reduce - ?
print(b.sum()) # 21
print(b.sum(axis=0)) # array([5, 7, 9]), сумма по i
# но это не fold или reduce, мы не можем передать суммирующую функцию :(

from functools import reduce
# lambda function!
print(reduce(lambda x,y: x - y, a)) # -19

# Фильтрация. boolean indexing, boolean masking
bi = (c%2 == 1)
print(bi)
# [[False,  True],
# [ True, False]], dtype=bool
print(c[bi]) # [3, 5] # matrix as index,
                      # [i] ведут себя по-разному в зависимости от типа i
print(c[c%2 == 1]) # alternative
print(c[c > 2]) # [3, 5, 6]
# two conditions together:
print(c[(c > 1) & (c %2 == 0)]) # [2, 6]; & and | are defined
                                # in numpy as vector operations!


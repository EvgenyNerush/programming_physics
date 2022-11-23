import matplotlib.pyplot as plt
from math import *

xs = [0.1 * i for i in range(100)] # not a good thing
ys = [sin(x) for x in xs]
zs = [cos(x**2) / (1 + x**2) for x in xs] # like map, but how to introduce local var?..

# size in inches! Matplotlib is so statefull...
plt.figure(figsize = (4,2.5), constrained_layout = True)

# keyword arguments
kwargs = {'color': 'r', 'linewidth': 0.7}

plt.plot(xs, ys, label = 'sin', **kwargs) # lisp alike
plt.plot(xs, zs, label = 'cos', **(kwargs | {'color': 'blue'}))

plt.legend()
plt.xlabel(r'$ct/\lambda$')
plt.ylabel(r'$f(t)$')

# plt.show() # Интерактивная!
plt.savefig('simple_matplotlib_plot.pdf')


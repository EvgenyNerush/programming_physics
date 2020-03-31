import matplotlib.pyplot as plt

def f(i):
    if i % 2 == 0:
        return int(i / 2)
    else:
        return 3 * i + 1

# начальные значения -- числа [3..31]
xs = list(range(3, 32))
n = len(xs)

# сгенерируем случайные цвета
# Lehmer RNG, 97 - простое,
# число 40 пораждает полную последовательность
randoms = []
s = 18 # seed
for i in range(n):
    s = 40 * s % 97
    randoms.append(s / 97)
# (r, g, b)
colors = [(r, 0.8, 1 - r) for r in randoms]

xll = xs[0]
for i in range(n):
    xarr = []
    yarr = []
    j = 0
    y = xs[i]
    while y != 1:
        xarr.append(j)
        yarr.append(y)
        j += 1
        y = f(y)
    print(i, j) # какое из i "проживет" дольше всех?
                # 24 живет 111 шагов
    plt.plot(xarr, yarr, "o-", color = colors[i], markeredgewidth=0)

plt.xlabel(r"$n$")
plt.ylabel(r"$(f^n)(x)$")
plt.savefig("collatz.png")

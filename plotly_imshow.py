import plotly.express as px
import numpy as np
from math import *

x0 = 0
x1 = 10
nx = 37

y0 = -5
y1 = 5
ny = 25

xs = np.linspace(x0, x1, nx) # or with list comprehenshion
ys = np.linspace(y0, y1, ny)

# rows, as for a matrix, from up to down
data = [[sin(x + y) for x in xs] for y in ys]

# see px.imshow? in ipython
fig = px.imshow(data,
        x = xs, # no need in half-pixel correction!
        y = ys,
        origin = 'lower',
        color_continuous_scale = 'aggrnyl', # hint: do not use divergent palettes
        labels = {'x': r'$\gamma$', 'y': r'$\hbar \omega$', 'color': 'n'})
# bug with 'color': r'$\sin(x + y)$' ?

resolution = 100 # pixels per cm

# in matplotlib you have error-prone default figure...
# and also confusion with xlim, set_xlim...
fig.update_xaxes(range = [x0, x1]) # limits
fig.update_yaxes(range = [y0, y1])
fig.update_layout(
    font_color = "black",
    font_size = 12,
    width = 8 * resolution, # cm to pixels
    height = 5 * resolution
)

fig.show()
#fig.write_image(fig, 'plotly_imshow.pdf') # `kaleido` package required


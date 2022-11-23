import plotly.express as px
from math import *

xs = [0.1 * x for x in range(0,100)]
ys = [exp(4 * sin(x)) for x in xs]

fig = px.line(x=xs, y=ys, title='Title')

fig.show() # in html browser
fig.write_json("simple_plotly_express.json")
# can be read to figure then with `plotly.io.read_json`

# requires `kaleido` python package
#fig.write_image('simple_plotly_express.pdf')


import pandas as pd

# A DataFrame is a table with named columns and indexed rows

# all of the same length
data = {'month': ['Nov', 'Dec', 'Jan'], 'apples': [9, 8, 5], 'oranges': [0, 7, 9]}

goods = pd.DataFrame(data)

# access with index
goods.loc[1] # month   'Dec'
             # apples  8
             # oranges 7

goods = pd.DataFrame(data, index = data['month']) # now index is a month
# or goods.set_index('month')
goods.loc['Dec'] # the result is the same

# CSV is a comma-separated-value format for table data; the first line is
# for the header
goods.to_csv('goods.csv')
#,month,apples,oranges # bug?
#Nov,Nov,9,0
#Dec,Dec,8,7
#Jan,Jan,5,9

# DataFrame
df = pd.read_csv('goods.csv', index_col = 'month')

# goods.to_json, pd.from_json; very useful for web APIs

# pandas can work with databases also
# import sqlite3
# con = sqlite3.connect("database.db")
# df = pd.read_sql_query("SELECT * FROM goods", con)

# let's do some data science with exemplary data from plotly
from plotly.data import *

# Each row represents a country on a given year
df = gapminder()

# colum names
print(df.columns)
# Index(['country', 'continent', 'year', 'lifeExp', 'pop', 'gdpPercap', 'iso_alpha', 'iso_num'],
# dtype = 'object')

df.rename(columns = {'lifeExp': 'expectancy', 'gdpPercap': 'GDP'}, inplace = True)

# alternative - using itaration
columns = [col for col in df]
print(columns)

# print first 5 rows (by default) to use as a visual reference
print(df.head())

# .tail() for five bottom rows is also available
print(df.tail())

print(df.info()) # number of rows and columns, datatype, memory usage
print('shape =', df.shape) # tuple (number of rows, number of columns)

# drop rows with N/A data (np.nan, None)
df.dropna(inplace = True)
# see also fillna

# some statistics for a column, not row (no `loc`)
print(df['expectancy'].describe())
#mean       59.474439
#std        12.917107
#min        23.599000 # what contry is it?
#max        82.603000

# extract columns
df = df[['country', 'year', 'expectancy', 'GDP']]

print(df.sort_values('expectancy').head(2))
# Rwanda in 1992 has lowest life expectancy of 24
print(df.sort_values('expectancy').tail(2))
# Japan in 2007 has life expectancy of 83!


# no `loc` here; very similar to numpy boolean masking
zimbabwe = df[ df['country'] == 'Zimbabwe' ]
#print(zimbabwe.head())
# 1692  Zimbabwe  1952      48.451  406.884115
# 1693  Zimbabwe  1957      50.469  518.764268...

# add colums with values from another column processed by an anonymous function
df['is life good'] = df['expectancy'].apply(lambda x: 'yes' if x > 70 else 'no')
print(df.head())
# 0  Afghanistan  1952 28.801  779.445314  no

# now we can search for something in plots
import plotly.express as px

# plotly express + pandas = love :)
mask = df['country'].isin(['Rwanda', 'Japan', 'Afghanistan', \
        'United Kingdom', 'Greece', 'Turkey'])
fig = px.line(df[mask], x = 'year', y = 'expectancy', color = 'country')
# a lot is doing automatically, for instance, axes labeling
fig.show()
# show is non-blocking contrary to matplotlib.pyplot.show()

mask = (df['year'] > 1960) & (df['year'] < 1970)
fig = px.scatter(df[mask], x = 'GDP', y = 'expectancy', color = 'country')
# => in Kuwait life expectancy was about 60-65 years for GDP / population
# much larger than in the others
fig.show()


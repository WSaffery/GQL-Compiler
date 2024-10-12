import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_csv("results/stats_summarised_full.csv")
print(df)

df_small = df[["Triplet", "Count"]]
print(df_small)

for row in df_small.itertuples(index = False):
    # print(row)
    triplet = row.Triplet[1:-1]
    a, b, c = triplet.split(", ")
    count = row.Count;
    print(f'Map.entry(Triplet.with("{a}", "{b}", "{c}"), {count}L),')
    

# df_small.plot.bar(x="Triplet",y="Count")
# plt.show()

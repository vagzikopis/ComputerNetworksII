import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

sns.set_style("darkgrid")
path = "session-25-11-2020/"
echo_code = ""

# # Echo Function
# # G1 Delay Response times
# df = pd.read_csv(path+".csv")
# print(df.ResponseTimes.mean())
# print(df.ResponseTimes.var())
# sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.5)
# plt.title('G1: Delay Response Times')
# # Set y-axis label
# plt.ylabel('Response Time (Milliseconds)')
# # Set x-axis label
# plt.xlabel('Packets')
# plt.tight_layout()
# plt.savefig(path+"G1.png")
# plt.show()

# # # G2 Delay Throughput
# df = pd.read_csv(path+"R"+echo_code+".csv")
# sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.5)
# plt.title('G2: Non-Delay Throughput')
# # Set y-axis label
# plt.ylabel('Throughput')
# # Set x-axis label
# plt.xlabel('Packets')
# plt.tight_layout()
# plt.savefig(path+"G2.png")
# plt.show()

# # G3 Non-Delay Response times
# df = pd.read_csv(path+"E0000.csv")
# print(df.ResponseTimes.mean())
# print(df.ResponseTimes.var())
# sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.5)
# plt.title('G3: Non-Delay Response Times')
# # Set x-axis label
# plt.xlabel('Packets')
# # Set y-axis label
# plt.ylabel('Milliseconds')
# plt.tight_layout()
# plt.savefig(path+"G3.png")
# plt.show()

# # # G4 Non-Delay Throughput
# df = pd.read_csv(path+"RE0000.csv")
# sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.5)
# plt.title('G4: Non-Delay Throughput')
# # Set x-axis label
# plt.ylabel('Throughput')
# # Set y-axis label
# plt.xlabel('Packets')
# plt.tight_layout()
# plt.savefig(path+"G4.png")
# plt.show()

# # G5 Delay Response Times Histogram
# df = pd.read_csv(path+"R"+echo_code+".csv")
# sns.displot(df, x=df.ResponseTimes, bins=12)
# plt.title('G5: Delay Response Times Histogram')
# plt.tight_layout()
# plt.savefig(path+"G5.png")
# plt.show()

# # G6 Non-Delay Response Times Histogram
# df = pd.read_csv(path+"E0000.csv")
# df = df[df.ResponseTimes < 500]
# sns.displot(df, x=df.ResponseTimes)
# plt.title('G6: Non-Delay Response Times Histogram')
# plt.tight_layout()
# plt.savefig(path+"G6.png")
# plt.show()

# # G7 Delay Throughput Histogram
# df = pd.read_csv(path+"R"+echo_code+".csv")
# sns.displot(df, x=df.R, bins=30)
# plt.title('G7: Delay Throughput Times Histogram')
# plt.tight_layout()
# plt.savefig(path+"G7.png")
# plt.show()

# # G8 Delay Throughput Histogram
# df = pd.read_csv(path+"RE0000.csv")
# sns.displot(df, x=df.R, bins=30, kde=True)
# plt.title('G8: Non-Delay Throughput Times Histogram')
# plt.tight_layout()
# plt.savefig(path+"G8.png")
# plt.show()

# # TODO R1 
# df = pd.read_csv(path+".csv")
# print(df.head())
# a = 0.7
# b = 0.5
# g = 2.7
# df['SRTT'] = df['ResponseTimes']
# df['sigma'] = df['ResponseTimes'] / 2
# df['RTO'] = df['SRTT'] + 4 * df['sigma']
# df['RTT'] = df['ResponseTimes']
# for idx in df.index:
#     if (idx!= 0):
#         df.loc[idx, 'SRTT'] = a * df.loc[idx-1, 'SRTT'] + (1-a) * df.loc[idx, 'ResponseTimes']
#         df.loc[idx, 'sigma'] = b * df.loc[idx-1, 'sigma'] + (1-b) * abs((df.loc[idx, 'SRTT']-df.loc[idx, 'ResponseTimes']))
#         df.loc[idx, 'RTO'] = df.loc[idx, 'SRTT'] + g * df.loc[idx, 'sigma']
# print(df.head())
# sns.lineplot(data=df[['SRTT', 'RTO', 'RTT']])
# plt.title("R1: SRTT & RTO")
# # Set x-axis label
# plt.ylabel('Milliseconds')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"R1.png")    
# plt.show()

# # G9 DPCM song samples plot
# df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
# sns.lineplot(x=df.index, y=df.samples, linewidth = 0.1)
# plt.title("G9: DPCM 'At cell 33' Waveform")
# # Set x-axis label
# plt.ylabel('')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G9.png")
# plt.show()

# # G10 DPCM frequency generator samples plot
# df = pd.read_csv(path+"DPCM_SAMPLES_GENERATOR.csv")
# sns.lineplot(x=df.index, y=df.samples, linewidth = 0.1)
# plt.title('G10: DPCM Frequency Generator Waveform')
# # Set x-axis label
# plt.ylabel('')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G10.png")
# plt.show()

# # G11 DPCM song samples distribution
# df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
# sns.displot(df, x=df.samples, bins=15, kde=True)
# plt.title("G11: DPCM 'At cell 33' Samples Distribution")
# plt.tight_layout()
# plt.savefig(path+"G11.png")
# plt.show()

# # G12 DPCM song differences distribution
# df = pd.read_csv(path+"DPCM_DIFFS_GENERATOR.csv")
# sns.displot(df, x=df.differences, bins=15, kde=True)
# plt.title("G12: DPCM 'At cell 33' Differences Distribution")
# plt.tight_layout()
# plt.savefig(path+"G12.png")
# plt.show()

# # G13 AQDPCM song1 samples distribution
# df = pd.read_csv(path+"AQDPCM_SAMPLES_SONG_L33.csv")
# sns.displot(df, x=df.samples, bins=15, kde=True)
# plt.title("G13: AQDPCM 'At cell 33' Samples Distribution")
# plt.tight_layout()
# plt.savefig(path+"G13.png")
# plt.show()

# # G14 AQDPCM song1 differences distribution
# df = pd.read_csv(path+"AQDPCM_DIFFS_SONG_L33.csv")
# sns.displot(df, x=df.differences, bins=15, kde=True)
# plt.title("G14: AQDPCM 'At cell 33' Differences Distribution")
# plt.tight_layout()
# plt.savefig(path+"G14.png")
# plt.show()

# # G15 AQDPCM Song1 means plot
# df = pd.read_csv(path+"AQDPCM_MEANS_SONG_L33.csv")
# sns.lineplot(x=df.index, y=df.means, linewidth = 0.5)
# plt.title("G15: AQDPCM 'At cell 33' Mean Values")
# # Set x-axis label
# plt.ylabel('Mean')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G15.png")
# plt.show()

# # G16 AQDPCM Song1 steps plot
# df = pd.read_csv(path+"AQDPCM_STEPS_SONG_L33.csv")
# sns.lineplot(x=df.index, y=df.steps, linewidth = 0.5)
# plt.title("G16: AQDPCM 'At cell 33' Step Values")
# # # Set x-axis label
# plt.ylabel('Steps')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G16.png")
# plt.show()

# # G17 AQDPCM Song2 means plot
# df = pd.read_csv(path+"AQDPCM_MEANS_SONG_L20.csv")
# sns.lineplot(x=df.index, y=df.means, linewidth = 0.5)
# plt.title("G17: AQDPCM 'That's my name' Mean Values")
# # Set x-axis label
# plt.ylabel('Mean')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G17.png")
# plt.show()

# # G18 AQDPCM Song2 steps plot
# df = pd.read_csv(path+"AQDPCM_STEPS_SONG_L20.csv")
# sns.lineplot(x=df.index, y=df.steps, linewidth = 0.5)
# plt.title("G18: AQDPCM 'That's my name' Step Values")
# # Set x-axis label
# plt.ylabel('Steps')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G18.png")
# plt.show()

# #G19
# df = pd.read_csv(path+"COPTER1.csv")
# df['copter'] = df['copter'].str.split(r"\bALTITUDE\b", expand = True)[1]
# df['copter'] = df['copter'].str.split(r"\bTEMPERATURE\b", expand = True)[0]
# df['copter'] = df['copter'].str.replace("=0", "")
# df['copter'] = df['copter'].str.replace("=", "")
# df['copter'] = df['copter'].astype(int)
# sns.lineplot(x=df.index, y=df.copter, linewidth = 1)
# plt.title("G19: Copter Altitude")
# # Set x-axis label
# plt.ylabel('Altitude')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G19.png")
# plt.show()

# #G20
# df = pd.read_csv(path+"COPTER2.csv")
# df['copter'] = df['copter'].str.split(r"\bALTITUDE\b", expand = True)[1]
# df['copter'] = df['copter'].str.split(r"\bTEMPERATURE\b", expand = True)[0]
# df['copter'] = df['copter'].str.replace("=0", "")
# df['copter'] = df['copter'].str.replace("=", "")
# df['copter'] = df['copter'].astype(int)
# sns.lineplot(x=df.index, y=df.copter, linewidth = 1)
# plt.title("G19: Copter Altitude")
# # Set x-axis label
# plt.ylabel('Altitude')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.tight_layout()
# plt.savefig(path+"G20.png")
# plt.show()

# # OBD Plot
# files = ['airTemperature.csv', 'throttlePosition.csv', 'engineRPM.csv', 'vehicleSpeed.csv', 'coolantTemperature.csv']
# df = pd.read_csv(path+'engineRuntime.csv')
# for f in files:
#     temp = pd.read_csv(path+f)
#     print(temp.head())
#     df = df.join(temp)

# colors = plt.rcParams["axes.prop_cycle"]()
# f, axes = plt.subplots(3, 2, figsize=(10,8))
# f.suptitle("OnBoard Diagnostics")
# c = next(colors)["color"]
# axes[0][0].title.set_text('Engine Runtime')
# sns.lineplot(  y=df["engineRuntime"], x= df.index, data=df, ax=axes[0][0], color=c, linewidth=1)

# c = next(colors)["color"]
# axes[0][1].title.set_text('Throttle Position')
# sns.lineplot(  y=df["throttlePosition"], x= df.index, data=df, ax=axes[0][1], color=c, linewidth=1)

# c = next(colors)["color"]
# axes[1][0].title.set_text('Air Temperature')
# axes[1][0].set(ylabel = "Celcius")
# sns.lineplot(  y=df["airTemperature"], x= df.index, data=df, ax=axes[1][0], color=c, linewidth=1)

# c = next(colors)["color"]
# axes[1][1].title.set_text('Coolant Temperature')
# axes[1][1].set(ylabel = "Celcius")
# sns.lineplot(  y=df["coolantTemperature"], x= df.index, data=df, ax=axes[1][1], color=c, linewidth=1)

# c = next(colors)["color"]
# axes[2][0].title.set_text('Engine RPM')
# axes[2][0].set(ylabel = "RPM")
# sns.lineplot(  y=df["engineRPM"], x= df.index, data=df, ax=axes[2][0], color=c, linewidth=1)

# c = next(colors)["color"]
# axes[2][1].title.set_text('Vehicle Speed')
# axes[2][1].set(ylabel = "Measure")
# sns.lineplot(  y=df["vehicleSpeed"], x= df.index, data=df, ax=axes[2][1], color=c, linewidth=1)

# f.tight_layout()
# f.savefig(path+"OBD.png")
# plt.show()

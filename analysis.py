import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

sns.set_style("darkgrid")
path = "session-16-11-2020/"
# # # Echo Function
# # G1 Delay Response times
# df = pd.read_csv(path+"E3547.csv")
# sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.2)
# plt.title('G1: Non-Delay Response Times')
# # Set x-axis label
# plt.xlabel('Milliseconds')
# # Set y-axis label
# plt.ylabel('Packets')
# plt.show()

# G2 Delay Throughput
# df = pd.read_csv(path+"RE3547.csv")
# sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.2)
# plt.title('G2: Non-Delay Throughput')
# # Set x-axis label
# plt.ylabel('Throughput')
# # Set y-axis label
# plt.xlabel('Packets')
# plt.show()

# G3 Non-Delay Response times
# df = pd.read_csv(path+"E0000.csv")
# sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.2)
# plt.title('G3: Non-Delay Response Times\nMean Value:'+str(df.ResponseTimes.mean())[0:5])
# # Set x-axis label
# plt.xlabel('Milliseconds')
# # Set y-axis label
# plt.ylabel('Packets')
# plt.show()

# G4 Non-Delay Throughput
# df = pd.read_csv(path+"RE0000.csv")
# sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.2)
# plt.title('G4: Non-Delay Throughput')
# # Set x-axis label
# plt.ylabel('Throughput')
# # Set y-axis label
# plt.xlabel('Packets')
# plt.show()

# # G5 Delay Response Times Histogram
# df = pd.read_csv(path+"E3547.csv")
# sns.displot(df, x=df.ResponseTimes, bins=20)
# plt.title('G5: Delay Response Times Histogram')
# plt.show()

# # G6 Non-Delay Response Times Histogram
# df = pd.read_csv(path+"E3547.csv")
# sns.displot(df, x=df.ResponseTimes, bins=20)
# plt.title('G6: Non-Delay Response Times Histogram')
# plt.show()

# # G7 Delay Throughput Histogram
# df = pd.read_csv(path+"RE3547.csv")
# sns.displot(df, x=df.R, bins=15)
# plt.title('G7: Delay Throughput Times Histogram')
# plt.show()

# # G8 Delay Throughput Histogram
# df = pd.read_csv(path+"RE0000.csv")
# sns.displot(df, x=df.R, bins=15, kde=True)
# plt.title('G8: Non-Delay Throughput Times Histogram')
# plt.show()

# TODO R1 

# # G9 DPCM song samples plot
# df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
# sns.lineplot(x=df.index, y=df.samples, linewidth = 0.2)
# plt.title("G9: DPCM 'My Number One' Waveform")
# # Set x-axis label
# plt.ylabel('Frequency (Hz)')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.show()

# # G10 DPCM frequency generator samples plot
# df = pd.read_csv(path+"DPCM_SAMPLES_GENERATOR.csv")
# sns.lineplot(x=df.index, y=df.samples, linewidth = 0.2)
# plt.title('G10: DPCM Frequency Generator Waveform')
# # Set x-axis label
# plt.ylabel('Frequency (Hz)')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.show()

# # G11 DPCM song samples distribution
# df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
# sns.displot(df, x=df.samples, bins=15, kde=True)
# plt.title("G11: DPCM 'My Number One' Samples Distribution")
# plt.show()

# # G12 DPCM song differences distribution
# df = pd.read_csv(path+"DPCM_DIFFS_GENERATOR.csv")
# sns.displot(df, x=df.differences, bins=15, kde=True)
# plt.title("G12: DPCM 'My Number One' Differences Distribution")
# plt.show()

# # G13 AQDPCM song1 samples distribution
# df = pd.read_csv(path+"AQDPCM_SAMPLES_SONG_L01.csv")
# sns.displot(df, x=df.samples, bins=15, kde=True)
# plt.title("G13: AQDPCM 'My Number One' Samples Distribution")
# plt.show()

# # G14 AQDPCM song1 differences distribution
# df = pd.read_csv(path+"AQDPCM_DIFFS_SONG_L01.csv")
# sns.displot(df, x=df.differences, bins=15, kde=True)
# plt.title("G14: AQDPCM 'My Number One' Differences Distribution")
# plt.show()

# # G15 AQDPCM Song1 means plot
# df = pd.read_csv(path+"AQDPCM_MEANS_SONG_L01.csv")
# sns.lineplot(x=df.index, y=df.means, linewidth = 1)
# plt.title("G15: 'My Number One' Mean Values")
# # Set x-axis label
# plt.ylabel('Mean')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.show()

# # G16 AQDPCM Song1 steps plot
# df = pd.read_csv(path+"AQDPCM_STEPS_SONG_L01.csv")
# sns.lineplot(x=df.index, y=df.steps, linewidth = 1)
# plt.title("G16: 'My Number One' Step Values")
# # # Set x-axis label
# plt.ylabel('Steps')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.show()

# # G17 AQDPCM Song2 means plot
# df = pd.read_csv(path+"AQDPCM_MEANS_SONG_L03.csv")
# sns.lineplot(x=df.index, y=df.means, linewidth = 1)
# plt.title("G17: 'Symphony No.9' Mean Values")
# # Set x-axis label
# plt.ylabel('Mean')
# # Set y-axis label
# plt.xlabel('Samples')
# plt.show()

# # G18 AQDPCM Song2 steps plot
# df = pd.read_csv(path+"AQDPCM_STEPS_SONG_L03.csv")
# sns.lineplot(x=df.index, y=df.steps, linewidth = 1)
# plt.title("G18: 'Symphony No.9' Step Values")
# # Set x-axis label
# plt.ylabel('Steps')
# # Set y-axis label
# plt.xlabel('Samples')
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
# plt.show()

# # OBD Plot
# files = ['airTemperature.csv', 'throttlePosition.csv', 'engineRPM.csv', 'vehicleSpeed.csv', 'coolantTemperature.csv']
# df = pd.read_csv(path+'engineRuntime.csv')
# for f in files:
#     temp = pd.read_csv(path+f)
#     print(temp.head())
#     df = df.join(temp)

# colors = plt.rcParams["axes.prop_cycle"]()
# f, axes = plt.subplots(3, 2)
# f.suptitle("OnBoard Diagnostics")
# c = next(colors)["color"]
# axes[0][0].title.set_text('Engine Runtime')
# sns.lineplot(  y=df["engineRuntime"], x= df.index, data=df, ax=axes[0][0], color=c)

# c = next(colors)["color"]
# axes[0][1].title.set_text('Throttle Position')
# sns.lineplot(  y=df["throttlePosition"], x= df.index, data=df, ax=axes[0][1], color=c)

# c = next(colors)["color"]
# axes[1][0].title.set_text('Air Temperature')
# axes[1][0].set(ylabel = "Celcius")
# sns.lineplot(  y=df["airTemperature"], x= df.index, data=df, ax=axes[1][0], color=c)

# c = next(colors)["color"]
# axes[1][1].title.set_text('Coolant Temperature')
# axes[1][1].set(ylabel = "Celcius")
# sns.lineplot(  y=df["coolantTemperature"], x= df.index, data=df, ax=axes[1][1], color=c)

# c = next(colors)["color"]
# axes[2][0].title.set_text('Engine RPM')
# axes[2][0].set(ylabel = "RPM")
# sns.lineplot(  y=df["engineRPM"], x= df.index, data=df, ax=axes[2][0], color=c)

# c = next(colors)["color"]
# axes[2][1].title.set_text('Vehicle Speed')
# axes[2][1].set(ylabel = "Measure")
# sns.lineplot(  y=df["vehicleSpeed"], x= df.index, data=df, ax=axes[2][1], color=c)

# plt.show()

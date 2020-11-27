import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

global echo_code
global path

# Echo Function
# G1 Delay Response times
def G1():
    df = pd.read_csv(path+echo_code+".csv")
    print("Mean: " + df.ResponseTimes.mean())
    print("Var: " + df.ResponseTimes.var())
    sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.5)
    plt.title('G1: Delay Response Times')
    # Set y-axis label
    plt.ylabel('Response Time (Milliseconds)')
    # Set x-axis label
    plt.xlabel('Packets')
    plt.tight_layout()
    plt.savefig(path+"G1.png")
    plt.show()

# # G2 Delay Throughput
def G2():
    df = pd.read_csv(path+"R"+echo_code+".csv")
    sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.5)
    plt.title('G2: Non-Delay Throughput')
    # Set y-axis label
    plt.ylabel('Throughput')
    # Set x-axis label
    plt.xlabel('Packets')
    plt.tight_layout()
    plt.savefig(path+"G2.png")
    plt.show()

# # G3 Non-Delay Response times
def G3():
    df = pd.read_csv(path+"E0000.csv")
    print(df.ResponseTimes.mean())
    print(df.ResponseTimes.var())
    sns.lineplot(x=df.index, y=df.ResponseTimes, data=df, linewidth = 0.5)
    plt.title('G3: Non-Delay Response Times')
    # Set x-axis label
    plt.xlabel('Packets')
    # Set y-axis label
    plt.ylabel('Milliseconds')
    plt.tight_layout()
    plt.savefig(path+"G3.png")
    plt.show()

# # # G4 Non-Delay Throughput
def G4():
    df = pd.read_csv(path+"RE0000.csv")
    sns.lineplot(x=df.index, y=df.R, data=df, linewidth = 0.5)
    plt.title('G4: Non-Delay Throughput')
    # Set x-axis label
    plt.ylabel('Throughput')
    # Set y-axis label
    plt.xlabel('Packets')
    plt.tight_layout()
    plt.savefig(path+"G4.png")
    plt.show()

# # G5 Delay Response Times Histogram
def G5():
    df = pd.read_csv(path+"R"+echo_code+".csv")
    sns.displot(df, x=df.ResponseTimes, bins=12)
    plt.title('G5: Delay Response Times Histogram')
    plt.tight_layout()
    plt.savefig(path+"G5.png")
    plt.show()

# # G6 Non-Delay Response Times Histogram
def G6():
    df = pd.read_csv(path+"E0000.csv")
    df = df[df.ResponseTimes < 500]
    sns.displot(df, x=df.ResponseTimes)
    plt.title('G6: Non-Delay Response Times Histogram')
    plt.tight_layout()
    plt.savefig(path+"G6.png")
    plt.show()

# # G7 Delay Throughput Histogram
def G7():
    df = pd.read_csv(path+"R"+echo_code+".csv")
    sns.displot(df, x=df.R, bins=30)
    plt.title('G7: Delay Throughput Times Histogram')
    plt.tight_layout()
    plt.savefig(path+"G7.png")
    plt.show()

# # G8 Delay Throughput Histogram
def G8():
    df = pd.read_csv(path+"RE0000.csv")
    sns.displot(df, x=df.R, bins=30, kde=True)
    plt.title('G8: Non-Delay Throughput Times Histogram')
    plt.tight_layout()
    plt.savefig(path+"G8.png")
    plt.show()

# # TODO R1 
def R1(a=0.7, b=0.5, g=2.7):
    df = pd.read_csv(path+echo_code+".csv")
    df['SRTT'] = df['ResponseTimes']
    df['sigma'] = df['ResponseTimes'] / 2
    df['RTO'] = df['SRTT'] + 4 * df['sigma']
    df['RTT'] = df['ResponseTimes']
    for idx in df.index:
        if (idx!= 0):
            df.loc[idx, 'SRTT'] = a * df.loc[idx-1, 'SRTT'] + (1-a) * df.loc[idx, 'ResponseTimes']
            df.loc[idx, 'sigma'] = b * df.loc[idx-1, 'sigma'] + (1-b) * abs((df.loc[idx, 'SRTT']-df.loc[idx, 'ResponseTimes']))
            df.loc[idx, 'RTO'] = df.loc[idx, 'SRTT'] + g * df.loc[idx, 'sigma']
    sns.lineplot(data=df[['SRTT', 'RTO', 'RTT']])
    plt.title("R1: SRTT & RTO")
    # Set x-axis label
    plt.ylabel('Milliseconds')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"R1.png")    
    plt.show()

# # G9 DPCM song samples plot
def G9():
    df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
    sns.lineplot(x=df.index, y=df.samples, linewidth = 0.1)
    plt.title("G9: DPCM Song 1 Waveform")
    # Set x-axis label
    plt.ylabel('')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G9.png")
    plt.show()

# # G10 DPCM frequency generator samples plot
def G10():
    df = pd.read_csv(path+"DPCM_SAMPLES_GENERATOR.csv")
    sns.lineplot(x=df.index, y=df.samples, linewidth = 0.1)
    plt.title('G10: DPCM Frequency Generator Waveform')
    # Set x-axis label
    plt.ylabel('')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G10.png")
    plt.show()

# # G11 DPCM song samples distribution
def G11():
    df = pd.read_csv(path+"DPCM_SAMPLES_SONG.csv")
    sns.displot(df, x=df.samples, bins=15, kde=True)
    plt.title("G11: DPCM Song 1 Samples Distribution")
    plt.tight_layout()
    plt.savefig(path+"G11.png")
    plt.show()

# # G12 DPCM song differences distribution
def G12():
    df = pd.read_csv(path+"DPCM_DIFFS_GENERATOR.csv")
    sns.displot(df, x=df.differences, bins=15, kde=True)
    plt.title("G12: DPCM Song 1 Differences Distribution")
    plt.tight_layout()
    plt.savefig(path+"G12.png")
    plt.show()

# G13 AQDPCM song1 samples distribution
def G13():
    df = pd.read_csv(path+"AQDPCM_SAMPLES_SONG_L33.csv")
    sns.displot(df, x=df.samples, bins=15, kde=True)
    plt.title("G13: AQDPCM Song 1 Samples Distribution")
    plt.tight_layout()
    plt.savefig(path+"G13.png")
    plt.show()

# # G14 AQDPCM song1 differences distribution
def G14():
    df = pd.read_csv(path+"AQDPCM_DIFFS_SONG_L33.csv")
    sns.displot(df, x=df.differences, bins=15, kde=True)
    plt.title("G14: AQDPCM Song 1 Differences Distribution")
    plt.tight_layout()
    plt.savefig(path+"G14.png")
    plt.show()

# # G15 AQDPCM Song1 means plot
def G15():
    df = pd.read_csv(path+"AQDPCM_MEANS_SONG_L33.csv")
    sns.lineplot(x=df.index, y=df.means, linewidth = 0.5)
    plt.title("G15: AQDPCM Song 1 Mean Values")
    # Set x-axis label
    plt.ylabel('Mean')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G15.png")
    plt.show()

# # G16 AQDPCM Song1 steps plot
def G16():
    df = pd.read_csv(path+"AQDPCM_STEPS_SONG_L33.csv")
    sns.lineplot(x=df.index, y=df.steps, linewidth = 0.5)
    plt.title("G16: AQDPCM Song 1 Step Values")
    # # Set x-axis label
    plt.ylabel('Steps')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G16.png")
    plt.show()

# # G17 AQDPCM Song2 means plot
def G17():
    df = pd.read_csv(path+"AQDPCM_MEANS_SONG.csv")
    sns.lineplot(x=df.index, y=df.means, linewidth = 0.5)
    plt.title("G17: AQDPCM Song 2 Mean Values")
    # Set x-axis label
    plt.ylabel('Mean')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G17.png")
    plt.show()

# # G18 AQDPCM Song2 steps plot
def G18():
    df = pd.read_csv(path+"AQDPCM_STEPS_SONG.csv")
    sns.lineplot(x=df.index, y=df.steps, linewidth = 0.5)
    plt.title("G18: AQDPCM Song 2 Step Values")
    # Set x-axis label
    plt.ylabel('Steps')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G18.png")
    plt.show()

# #G19
def G19():
    df = pd.read_csv(path+"COPTER1.csv")
    df['LMOTOR'] = df['copter'].str.split(r"\bLMOTOR\b", expand = True)[1]
    df['LMOTOR'] = df['LMOTOR'].str.split(r"\bRMOTOR\b", expand = True)[0]
    df['LMOTOR'] = df['LMOTOR'].str.replace("=", "")
    df['LMOTOR'] = df['LMOTOR'].astype(int)
    
    df['RMOTOR'] = df['copter'].str.split(r"\bRMOTOR\b", expand = True)[1]
    df['RMOTOR'] = df['RMOTOR'].str.split(r"\bALTITUDE\b", expand = True)[0]
    df['RMOTOR'] = df['RMOTOR'].str.replace("=", "")
    df['RMOTOR'] = df['RMOTOR'].astype(int)

    df['TEMPERATURE'] = df['copter'].str.split(r"\bTEMPERATURE\b", expand = True)[1]
    df['TEMPERATURE'] = df['TEMPERATURE'].str.split(r"\bPRESSURE\b", expand = True)[0]
    df['TEMPERATURE'] = df['TEMPERATURE'].str.replace("=", "")
    df['TEMPERATURE'] = df['TEMPERATURE'].str.replace("+", "")
    df['TEMPERATURE'] = df['TEMPERATURE'].astype(float)

    df['PRESSURE'] = df['copter'].str.split(r"\bPRESSURE\b", expand = True)[1]
    df['PRESSURE'] = df['PRESSURE'].str.split(r"\bTELEMETRY\b", expand = True)[0]
    df['PRESSURE'] = df['PRESSURE'].str.replace("=", "")
    df['PRESSURE'] = df['PRESSURE'].astype(float)

    df['ALTITUDE'] = df['copter'].str.split(r"\bALTITUDE\b", expand = True)[1]
    df['ALTITUDE'] = df['ALTITUDE'].str.split(r"\bTEMPERATURE\b", expand = True)[0]
    df['ALTITUDE'] = df['ALTITUDE'].str.replace("=", "")
    df['ALTITUDE'] = df['ALTITUDE'].astype(int)
    
    sns.lineplot(data=df[['ALTITUDE','TEMPERATURE','LMOTOR','RMOTOR','PRESSURE']], linewidth = 2)
    plt.title("G19: Copter Flight 1 Metrics")
    # Set x-axis label
    plt.ylabel('')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G19.png")
    plt.show()

# #G20
def G20():
    df = pd.read_csv(path+"COPTER2.csv")
    df['LMOTOR'] = df['copter'].str.split(r"\bLMOTOR\b", expand = True)[1]
    df['LMOTOR'] = df['LMOTOR'].str.split(r"\bRMOTOR\b", expand = True)[0]
    df['LMOTOR'] = df['LMOTOR'].str.replace("=", "")
    df['LMOTOR'] = df['LMOTOR'].astype(int)
    
    df['RMOTOR'] = df['copter'].str.split(r"\bRMOTOR\b", expand = True)[1]
    df['RMOTOR'] = df['RMOTOR'].str.split(r"\bALTITUDE\b", expand = True)[0]
    df['RMOTOR'] = df['RMOTOR'].str.replace("=", "")
    df['RMOTOR'] = df['RMOTOR'].astype(int)

    df['TEMPERATURE'] = df['copter'].str.split(r"\bTEMPERATURE\b", expand = True)[1]
    df['TEMPERATURE'] = df['TEMPERATURE'].str.split(r"\bPRESSURE\b", expand = True)[0]
    df['TEMPERATURE'] = df['TEMPERATURE'].str.replace("=", "")
    df['TEMPERATURE'] = df['TEMPERATURE'].str.replace("+", "")
    df['TEMPERATURE'] = df['TEMPERATURE'].astype(float)

    df['PRESSURE'] = df['copter'].str.split(r"\bPRESSURE\b", expand = True)[1]
    df['PRESSURE'] = df['PRESSURE'].str.split(r"\bTELEMETRY\b", expand = True)[0]
    df['PRESSURE'] = df['PRESSURE'].str.replace("=", "")
    df['PRESSURE'] = df['PRESSURE'].astype(float)

    df['ALTITUDE'] = df['copter'].str.split(r"\bALTITUDE\b", expand = True)[1]
    df['ALTITUDE'] = df['ALTITUDE'].str.split(r"\bTEMPERATURE\b", expand = True)[0]
    df['ALTITUDE'] = df['ALTITUDE'].str.replace("=", "")
    df['ALTITUDE'] = df['ALTITUDE'].astype(int)
    
    sns.lineplot(data=df[['ALTITUDE','TEMPERATURE','LMOTOR','RMOTOR','PRESSURE']], linewidth = 2)
    plt.title("G20: Copter Flight 2 Metrics")
    # Set x-axis label
    plt.ylabel('')
    # Set y-axis label
    plt.xlabel('Samples')
    plt.tight_layout()
    plt.savefig(path+"G20.png")
    plt.show()

# # OBD Plot
def OBD():
    files = ['airTemperature.csv', 'throttlePosition.csv', 'engineRPM.csv', 'vehicleSpeed.csv', 'coolantTemperature.csv']
    df = pd.read_csv(path+'engineRuntime.csv')
    for f in files:
        temp = pd.read_csv(path+f)
        df = df.join(temp)

    colors = plt.rcParams["axes.prop_cycle"]()
    f, axes = plt.subplots(3, 2, figsize=(10,8))
    f.suptitle("OnBoard Diagnostics")
    c = next(colors)["color"]
    axes[0][0].title.set_text('Engine Runtime')
    sns.lineplot(  y=df["engineRuntime"], x= df.index, data=df, ax=axes[0][0], color=c, linewidth=1)

    c = next(colors)["color"]
    axes[0][1].title.set_text('Throttle Position')
    sns.lineplot(  y=df["throttlePosition"], x= df.index, data=df, ax=axes[0][1], color=c, linewidth=1)

    c = next(colors)["color"]
    axes[1][0].title.set_text('Air Temperature')
    axes[1][0].set(ylabel = "Celcius")
    sns.lineplot(  y=df["airTemperature"], x= df.index, data=df, ax=axes[1][0], color=c, linewidth=1)

    c = next(colors)["color"]
    axes[1][1].title.set_text('Coolant Temperature')
    axes[1][1].set(ylabel = "Celcius")
    sns.lineplot(  y=df["coolantTemperature"], x= df.index, data=df, ax=axes[1][1], color=c, linewidth=1)

    c = next(colors)["color"]
    axes[2][0].title.set_text('Engine RPM')
    axes[2][0].set(ylabel = "RPM")
    sns.lineplot(  y=df["engineRPM"], x= df.index, data=df, ax=axes[2][0], color=c, linewidth=1)

    c = next(colors)["color"]
    axes[2][1].title.set_text('Vehicle Speed')
    axes[2][1].set(ylabel = "Measure")
    sns.lineplot(  y=df["vehicleSpeed"], x= df.index, data=df, ax=axes[2][1], color=c, linewidth=1)

    f.tight_layout()
    f.savefig(path+"OBD.png")
    plt.show()

def T():
    df = pd.read_csv(path+echo_code+"T00TEMPERATURES.csv")
    df['messages'] = df['messages'].apply(lambda x: x.split("+")[1])
    df['messages'] = df['messages'].apply(lambda x: x.split("C")[0])
    df['messages'] = df['messages'].apply(lambda x: x.strip())
    df['temperatures'] = df['messages'].astype(int)
    sns.lineplot(x=df.index, y=df['temperatures'])
    plt.xlabel('Temperatures (Celcius)')
    plt.xlabel('Packets')
    plt.show()

if __name__ == "__main__":
    sns.set_style("darkgrid")
    path = "session/"
    G19()
    G20()
    flag = True
    while flag:
        graph = input("Enter code of the graph to be displayed (G1-20, R1, OBD):")
        print(graph) 
        if graph == "T":
            echo_code = input("Enter echo request code:")
            print(echo_code)
            T()
        elif graph == "G1":
            echo_code = input("Enter echo request code:")
            print(echo_code)
            G1()
        elif graph == "G2":
            G2()
        elif graph == "G3":
            G3()
        elif graph == "G4":
            G4()
        elif graph == "G5":
            echo_code = input("Enter echo request code:")
            print(echo_code)
            G5()
        elif graph == "G6":
            G6()
        elif graph == "G7":
            echo_code = input("Enter echo request code:")
            print(echo_code)
            G7()
        elif graph == "G8":
            G8()
        elif graph == "G9":
            G9()
        elif graph == "G10":
            G10()
        elif graph == "G11":
            G11()
        elif graph == "G12":
            G12()
        elif graph == "G13":
            G13()
        elif graph == "G14":
            G14()
        elif graph == "G15":
            G15()
        elif graph == "G16":
            G16()
        elif graph == "G17":
            G17()
        elif graph == "G18":
            G18()
        elif graph == "G19":
            G19()
        elif graph == "G20":
            G20()
        elif graph == "R1":
            echo_code = input("Enter echo request code: ")
            print(echo_code)
            R1()
        elif graph == "OBD":
            OBD()
        stop = input("Press q to quit, or c to continue: ")
        if stop == "q":
            flag = False

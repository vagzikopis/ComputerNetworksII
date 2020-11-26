# Computer Networks II - Socket Programming Assignment

## Communicating with the university server(ithaki) through UDP Protocol

This assignment was implemented regarding the course Computer Networks II of the Aristotle University of Thessaloniki. The application developed implements the below modes :

- Echo Mode: Receive delayed or non-delayed "echo" packages
- Image Mode: Receive live image packages and save them in .jpeg format
- Audio DPCM Mode: Receive packages with DPCM encoding and play the received sound
- Audio AQDPCM Mode: Receive packages with AQDPCM encoding and play the received sound
- Copter Mode: Pilot the ithakicopter drone through TCP packets
- On Board Diagnostics Mode: Receive packets regarding on board vehicle diagnostics  such as engine runtime, coolant temperature etc

## How to execute

- Git clone the repository
- Enter valid request codes in the UserApplication.java file
- Run UserApplication

## Results

analysis.py provides graphs about the result of a specified session. analysis.py inputs:

- Session data path
- echo request code
- Code of the graph to be displayed (G1-20, R1, OBD)
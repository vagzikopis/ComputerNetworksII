import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader; 

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import java.text.SimpleDateFormat;
import java.util.Scanner;

/*
    @author Zikopis Evangelos
    ? Computer Networks II Assignment, 2020
    ? November 2020, Thessaloniki
*/
public class UserApplication {
    private static String echoRequestCode;
    private static String imageRequestCode;
    private static String audioRequestCode;
    private static String copterRequestCode;
    private static String vehicleRequestCode;
    private static int serverPort;
    private static int clientPort;
    private static final byte[] hostIP = { (byte)155,(byte)207,18,(byte)208 };
    private static final String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private static final String path = "session";

    public static final String COLOR_RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(final String[] args) throws IOException {
        System.out.println();
        System.out.println("\t\t\tAUTH Computer Networks II Assignment\t-\tZikopis Evangelos 8808");
        System.out.println();
        System.out.println("\t\t\t\t\tSession " + date + " has started");
        File theDir = new File(path);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        boolean flag = true;

        // reader used to read user's input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ENTER CLIENT PORT: ");
        String input = reader.readLine();  
        clientPort = Integer.parseInt(input);
        clientPort = 48030;
        System.out.println("ENTER SERVER PORT: ");
        input = reader.readLine(); 
        serverPort = Integer.parseInt(input);
        serverPort = 38030;

        while(flag) {
            System.out.println();
            System.out.println("\t\t\t\t\tFOR ECHO MODE TYPE 'E'\n");
            System.out.println("\t\t\t\t\tFOR AUDIO MODE TYPE 'A'\n");
            System.out.println("\t\t\t\t\tFOR IMAGE MODE TYPE 'M'\n");
            System.out.println("\t\t\t\t\tFOR COPTER MODE TYPE 'Q'\n");
            System.out.println("\t\t\t\t\tFOR VEHICLE MODE TYPE 'V'\n");
            
            System.out.println("Enter Mode Code: ");
            input = reader.readLine();  // Read user input

            if (input.equals("E") || input.equals("e")) {
                
                System.out.println("ENTER ECHO CODE");
                input = reader.readLine();  
                echoRequestCode = input;
                
                System.out.println(GREEN + "[ECHO DELAY] "+echoRequestCode + COLOR_RESET);
                echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 1);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(GREEN + "[ECHO NON-DELAY] "+echoRequestCode + COLOR_RESET);
                echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 2);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(GREEN + "[ECHO TEMPERATURES]"+echoRequestCode + COLOR_RESET);
                echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 3);
                sendVoid(serverPort, clientPort, hostIP);

            } else if (input.equals("A") || input.equals("a")) {

                System.out.println("ENTER AUDIO CODE");
                input = reader.readLine();  
                audioRequestCode = input;

                System.out.println(CYAN + "[DPCM] "+audioRequestCode + COLOR_RESET);
                dpcmSound(audioRequestCode, serverPort, clientPort, hostIP, 1, "L33");
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(CYAN + "[DPCM] "+audioRequestCode + COLOR_RESET);
                dpcmSound(audioRequestCode, serverPort, clientPort, hostIP, 2,"");
                sendVoid(serverPort, clientPort, hostIP);

                System.out.println(CYAN + "[AQDPCM] "+audioRequestCode + COLOR_RESET);
                aqdpcmSound(audioRequestCode, serverPort, clientPort, hostIP, "L33");
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(CYAN + "[AQDPCM] "+audioRequestCode + COLOR_RESET);
                aqdpcmSound(audioRequestCode, serverPort, clientPort, hostIP, "L20");
                sendVoid(serverPort, clientPort, hostIP);

            } else if (input.equals("M") || input.equals("m")) {

                System.out.println("ENTER IMAGE CODE");
                input = reader.readLine();  
                imageRequestCode = input;

                System.out.println(YELLOW + "[IMAGE input 1] "+imageRequestCode + COLOR_RESET);
                imageCapture(imageRequestCode, clientPort, serverPort, hostIP, 1);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(YELLOW + "[IMAGE input 2] "+imageRequestCode + COLOR_RESET);
                imageCapture(imageRequestCode, clientPort, serverPort, hostIP, 2);
                sendVoid(serverPort, clientPort, hostIP);

            } else if (input.equals("Q") || input.equals("q")) {

                System.out.println("ENTER COPTER CODE");
                input = reader.readLine();  
                copterRequestCode = input;

                System.out.println(PURPLE + "[ITHAKICOPTER]" + COLOR_RESET);
                ithakiCopter(copterRequestCode, 48078, hostIP, 1);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(PURPLE + "[ITHAKICOPTER]" + COLOR_RESET);
                ithakiCopter(copterRequestCode, 48078, hostIP, 2);
                sendVoid(serverPort, clientPort, hostIP);

            } else if ( input.equals("V") || input.equals("v") ) {
                
                System.out.println("ENTER VEHICLE CODE");
                input = reader.readLine();  
                vehicleRequestCode = input;

                String pid;
                System.out.println(RED + "[DIAGNOSTICS] Engine Run time" + COLOR_RESET);
                pid = "1F";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(RED + "[DIAGNOSTICS] Ithaki air temperature" + COLOR_RESET);
                pid = "0F";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(RED + "[DIAGNOSTICS] Throttle position" + COLOR_RESET);
                pid = "11";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(RED + "[DIAGNOSTICS] Engine RPM" + COLOR_RESET);
                pid = "0C";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(RED + "[DIAGNOSTICS] Vehicle speed" + COLOR_RESET);
                pid = "0D";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);
                sendVoid(serverPort, clientPort, hostIP);
                System.out.println(RED + "[DIAGNOSTICS] Coolant Temperature" + COLOR_RESET);
                pid = "05";
                onBoardDiagnostics(vehicleRequestCode, serverPort, clientPort, hostIP, pid);

            }
            System.out.println("Continue (y/n) ?");
            input = reader.readLine();  // Read user input
            if ( input.equals("y") || input.equals("Y") ) {
                flag = true;
            } else {
                flag = false;
            }
        }
        
    }


    /* 
    ? This function begins communication with the server and receives echo messages.
    * Mode 1: Delay mode. Server sends packets with delay
    * Mode 2: Non-Delay mode. Server sends packets without delay
    * Mode 2: Delay mode. Server sends packets with delay and temperature values
    */
    private static void echoPackages(final int clientPort, final int serverPort, String echoRequestCode, byte[] hostIP, int echoMode)
    throws IOException {
        double sessionTime;
        // Check mode
        if (echoMode == 1) {
            // Delay Mode
            echoRequestCode = echoRequestCode;
            sessionTime = 1*60*1000;
        } else if (echoMode == 2) {
            // Non Delay Mode
            echoRequestCode = "E0000";
            sessionTime = 1*60*1000;
        } else {
            // Temperature Mode
            echoRequestCode = echoRequestCode+"T00";
            sessionTime = 30*1000;
        }
        // Server's response
        String msg ="";
	    // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = echoRequestCode.getBytes();
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        // Socket used to receive packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        // Timeout 
        receiveSocket.setSoTimeout(4000);
        // Buffer for server's response
        byte[] rxbuffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Indexes used for while loop termination
        double start = 0, end = 0;
        // Indexes used to messure communication time
        double sendTime = 0, recTime = 0;
        ArrayList<Double> receiveTimes = new ArrayList<Double>();
        ArrayList<String> messages = new ArrayList<String>();
        // Packet counter
        int packetCounter = 0;
        // Initialize start index
        start = System.nanoTime();
        while(end < sessionTime)
        {
            // Send packet to server
            try {
                sendSocket.send(sendPacket);
                sendTime = System.nanoTime();
            } catch (final Exception x) {
                System.err.println("[ECHO]" + x);
            }
            // Receive response packet from server
            try {
                receiveSocket.receive(receivePacket);
                recTime = (System.nanoTime() - sendTime) / 1000000;
                receiveTimes.add(recTime);
                msg = new String(rxbuffer,0,receivePacket.getLength());
                System.out.println(msg);
                System.out.println("Communication time: " + recTime + " ms");
                messages.add(msg);
            } catch (final Exception x) {
                System.err.println("[ECHO]" + x);
            }
            end = (System.nanoTime() - start) / 1000000;
        }
        double avg = 0;
        // Total received packets 
        packetCounter = receiveTimes.size();
        if (packetCounter != 0) {
            avg = sessionTime / packetCounter;
        }
        // Print results
        System.out.println("Total packets received: " + packetCounter);
        System.out.println("Average time per packet: " + avg + " Milliseconds");
        System.out.println("Communication Time: " + sessionTime/(60*1000) + " Minute");
        
        BufferedWriter bw;
        // Save communication times to a file named "echoRequestCode".txt
        if (echoMode!=3) {
            bw = null;
            try {
                File file = new File(path+"/"+echoRequestCode+".csv");
                bw = new BufferedWriter( new FileWriter(file, false));
                if (!file.exists()){
                    file.createNewFile();
                }
                bw.write("ResponseTimes");
                bw.newLine();
                for(int i=0; i<receiveTimes.size(); i++)
                {
                    bw.write(String.valueOf(receiveTimes.get(i)));
                    bw.newLine();
                }
                bw.newLine();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            finally{
                try{
                    if(bw != null) bw.close();
                }catch(Exception ex){
                    System.out.println("[ECHO]Error in closing the BufferedWriter" + ex);
                }
            }
        } else {
            bw = null;
            try {
                File file = new File(path+"/"+echoRequestCode+"TEMPERATURES.csv");
                bw = new BufferedWriter( new FileWriter(file, false));
                if (!file.exists()){
                    file.createNewFile();
                }
                bw.write("messages");
                bw.newLine();
                for(int i=0; i<messages.size(); i++)
                {
                    bw.write(messages.get(i));
                    bw.newLine();
                }
                bw.newLine();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            finally{
                try{
                    if(bw != null) bw.close();
                }catch(Exception ex){
                    System.out.println("[ECHO]Error in closing the BufferedWriter" + ex);
                }
            }

        }
        // Throughput Calculation 
        double timeSum=0;
        // Packet counter to count packets inside the 8-sec window
        float throughputCounter=0;
        // Throughput array
        ArrayList<Float> R = new ArrayList<Float>();
        
        for(int i = 0; i < receiveTimes.size();i++){
            // Count the total packets after i-th packet for which 
            // time sums are under 8 seconds
            int j = i;
            while((timeSum < 8*1000)&&(j < receiveTimes.size())){
                timeSum += receiveTimes.get(j);
                throughputCounter++;
                j++;
            }
            // *32 = packet length
            // *8 to reduct to bits, since unit is bit/sec
            // /8 to fit in 8-sec window
            R.add(throughputCounter*32*8/8);
            throughputCounter = 0;
            timeSum = 0;
        }
        for(int i=0; i<R.size(); i++) {
            System.out.println("R:" + R.get(i));
        }

        // Save R (throughput values) at "R+echoRequestCode".txt
        if (echoMode!=3) {
            bw = null;
            try {
                File file = new File(path+"/"+"R"+echoRequestCode+".csv");
                bw = new BufferedWriter( new FileWriter(file, false));
                if (!file.exists()){
                    file.createNewFile();
                }
                bw.write("R");
                bw.newLine();
                for(int i=0; i<R.size(); i++)
                {
                    bw.write(String.valueOf(R.get(i)));
                    bw.newLine();
                }
                bw.newLine();
            }catch(IOException ioe){
                ioe.printStackTrace();
                System.out.println("[ECHO]" + ioe);

            }
            finally{
                try{
                    if(bw != null) bw.close();
                }catch(Exception ex){
                    System.out.println("[ECHO]Error in closing the BufferedWriter" + ex);
                }
            }
        }
        // Close communication sockets
        try{
            sendSocket.close();
        }catch(Exception ex){
            System.out.println("[ECHO]Error in closing the Send socket\n" + ex);
        }
        try{
            receiveSocket.close();
        }catch(Exception ex){
            System.out.println("[ECHO]Error in closing the Receive socket\n" + ex);
        }
        
    }

    /* 
    ? This function downloads an image sent from professor's live cameras
    * Mode 1: CAM=FIX
    * Mode 2: CAM=PTZ
    */
    private static void imageCapture(String imageRequestCode, int clientPort, int serverPort, byte[] hostIP, int imageMode)
    throws IOException {
        // Image mode selection
        if(imageMode == 1) {
            imageRequestCode = imageRequestCode + "\r";
        } else if (imageMode == 2) {
            imageRequestCode = imageRequestCode + " CAM=PTZ" + "\r";
        }
        
        // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = imageRequestCode.getBytes();    
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        // Socket used to receive image-packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        
        // Buffer for server's response
        byte[] rxbuffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Begin communication
        sendSocket.send(sendPacket);
        FileOutputStream imageFile = new FileOutputStream(path+"/"+imageRequestCode+".jpeg");
        // Timeout 
        receiveSocket.setSoTimeout(5000);
        // Begin the receiving packets process     
        System.out.println("... Downloading Image ...");
        for(;;) {
                try {
                    receiveSocket.receive(receivePacket);
                    // If the received packet length is less than 128 then 
                    // the image receiving process is over 
                    if (receivePacket.getLength() < 128) break;
                    // Save received bytes
                    for(int i=0; i<receivePacket.getLength(); i++) {
                        imageFile.write(rxbuffer[i]);
                    }
                } catch (IOException iox) {
                    System.out.println("[IMAGE] Communication Error "+iox);
                    break;
                }
        }
        System.out.println("Image Download Finished");
        // Close communication sockets
        try{
            sendSocket.close();
        }catch(Exception ex){
            System.out.println("[IMAGE]Error in closing the Send socket\n" + ex);
        }
        try{
            receiveSocket.close();
        }catch(Exception ex){
            System.out.println("[IMAGE]Error in closing the Receive socket\n" + ex);
        }
        // Close FileOutputStream
        try{
            imageFile.close();
        }catch (IOException iox) {
            System.out.println("[IMAGE]Error in closing the imageFile"+iox);
        }
        
    }

     /* 
    ? This function receives DPCM compressed sound packets. Packets are de-compressed
    ? and the sound clip is played.
    * MODE 1: Server sends a song
    * MODE 2: Server sends a sound clip from a high-frequency generator
    */
    private static void dpcmSound(String audioRequestCode, int serverPort, int clientPort, byte[] hostIP, int audioMode, String songCode)
    throws IOException {
        // Total datagram sound packets that will be received
        int totalPackets = 400;
        int mask1 = 15,mask2 = 240;
        // Quantitizer
        int b = 5,rx;
        // Nibbles used for decoding
        int nibble1,nibble2;
        // Differences
        int diff1,diff2;
        // Two sound samples
        int x1 = 0,x2 = 0;
        int counter = 0;
        ArrayList<Integer> differences = new ArrayList<Integer>();
        ArrayList<Integer> soundSamples = new ArrayList<Integer>();
        if (audioMode == 1) {
            audioRequestCode = audioRequestCode + songCode + "F" + totalPackets; 
        } else if (audioMode == 2) {
            audioRequestCode = audioRequestCode + "T" + totalPackets; 
        }
        // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = audioRequestCode.getBytes();    
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        // Socket used to receive image-packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        receiveSocket.setSoTimeout(3500);
        // Buffer for server's response
        byte[] rxbuffer = new byte[128];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Begin communication
        try {
            sendSocket.send(sendPacket);
        } catch (Exception ex) {
            System.out.println("[AUDIO]" + ex);
        }
        // Each packet corresponds to 256 sound soundSamples
        byte[] song = new byte[256*totalPackets];
        for(int i=0; i<totalPackets; i++) {
            try{
                receiveSocket.receive(receivePacket);
                System.out.println("Audio Packet:"+i);
                // Packet consists of 128 bytes of differential pairs
                for(int j=0; j<128; j++) {
                    rx = rxbuffer[j];
                    // Use mask 00001111 to catch first 4-bits or nibble 1
                    nibble1 = rx & mask1; 
                    // Use mask 11110000 to catch last 4-bits or nibble 2
                    nibble2 = (rx & mask2)>>4; 
                    // Subtract 8 from first nibble
                    diff1 = nibble1-8;   
                    differences.add(diff1);
                    // Multiply difference 1 with quantist b
                    diff1 = diff1*b;
                    // Subtract 8 from second nibble
                    diff2 = nibble2-8;
                    differences.add(diff2);
                    // Multiply difference 2 with quantist b
                    diff2 = diff2*b;
                    // First sound sample
                    x1 = x2 + diff1;
                    soundSamples.add(x1);
                    // Second sound sample
                    x2 = x1 + diff2;
                    soundSamples.add(x2);
                    // Construct audio-song
                    song[counter] = (byte)x1;
                    song[counter + 1] = (byte)x2;
                    counter += 2;
                }
            } catch (Exception ex) {    
                System.out.println("[AUDIO]" + ex);
            }
            
        }
        // Play the received song
        System.out.println("Playing the song");
        AudioFormat pcmFormat = new AudioFormat(8000,8,1,true,false);
        try{
            SourceDataLine playsong = AudioSystem.getSourceDataLine(pcmFormat);
            playsong.open(pcmFormat,32000);
            playsong.start();
            playsong.write(song,0,256*totalPackets);
            playsong.stop();
            playsong.close();
        } catch (Exception ex) {
            System.out.println("[AUDIO]" + ex);
        }
        
        // Close communication sockets
        try{
            sendSocket.close();
        }catch(Exception ex){
            System.out.println("[AUDIO]Error in closing the Send socket\n" + ex);
        }
        try{
            receiveSocket.close();
        }catch(Exception ex){
            System.out.println("[AUDIO]Error in closing the Receive socket\n" + ex);
        }
        BufferedWriter bw;
        // Save difference 
        bw = null;
        try {
            File file = null;
            if (audioMode == 1) {
                file = new File(path+"/"+"DPCM_DIFFS_SONG.csv");
            } else if (audioMode == 2) {
                file = new File(path+"/"+"DPCM_DIFFS_GENERATOR.csv"); 
            }
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write("differences");
            bw.newLine();
            // Write differences to a txt file
            for(int i = 0 ; i < differences.size() ; i ++){
                bw.write(differences.get(i).toString());
                bw.newLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
            }
        }
         // Save received sound to a .csv
         bw = null;
         try {
            File file = null;
            if (audioMode == 1) {
                file = new File(path+"/"+"DPCM_SAMPLES_SONG.csv");
            } else if (audioMode == 2) {
                file = new File(path+"/"+"DPCM_SAMPLES_GENERATOR.csv"); 
            }
             bw = new BufferedWriter( new FileWriter(file, false));
             if (!file.exists()){
                 file.createNewFile();
             }
            bw.write("samples");
            bw.newLine();
             // Write differences to a txt file
             for(int i = 0 ; i < soundSamples.size() ; i ++){
                 bw.write(soundSamples.get(i).toString());
                 bw.newLine();
             }
         }catch(IOException ioe){
             ioe.printStackTrace();
         }
         finally{
             try{
                 if(bw != null) bw.close();
             }catch(Exception ex){
                 System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
             }
         }
    }

    /* 
    ? This function receives AQDPCM compressed sound packets. Packets are de-compressed
    ? and the sound clip is played.
    */
    private static void aqdpcmSound(String audioRequestCode, int serverPort, int clientPort, byte[] hostIP, String songCode)
    throws IOException {
        // Total datagram sound packets that will be received
        int totalPackets = 600;
        // Bit masks
        int mask1 = 15,mask2 = 240;
        // Mean and step values
        int mean, b, rx;
        // Nibbles used for decoding
        int nibble1,nibble2;
        // Differences
        int diff1,diff2;
        // Two sound samples per packet
        int x1 = 0,x2 = 0;
        int counter = 0;
        int temp = 0;
        // Bit mask
        int signMask = 0x80;
        ArrayList<Integer> meanValues = new ArrayList<Integer>();
        ArrayList<Integer> bValues = new ArrayList<Integer>();
        ArrayList<Integer> differences = new ArrayList<Integer>();
        ArrayList<Integer> soundSamples = new ArrayList<Integer>();
        
        audioRequestCode = audioRequestCode + songCode + "AQF" + totalPackets;
        
        // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = audioRequestCode.getBytes();    
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        // Socket used to receive image-packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        receiveSocket.setSoTimeout(3500);
        // Buffer for server's response. AQDPCM requires 132 bytes per packet
        byte[] rxbuffer = new byte[132];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Begin communication
        try {
            sendSocket.send(sendPacket);
        } catch (Exception ex) {
            System.out.println("[AUDIO]" + ex);
        }
        // Each packet corresponds to 256*2 sound soundSamples
        byte[] song = new byte[256*2*totalPackets];
        // Byte values to break header
        byte[] meanByte = new byte[4];
        byte[] bByte = new byte[4];
        byte sign;
        for(int i=1; i<totalPackets; i++) {
            try{
                receiveSocket.receive(receivePacket);
                System.out.println("Audio Packet:"+i);
                // Header Structure: rxbuffer[0-1-2-3] = [meanLSB-meanMSB-bLSB-bMSB]
                //if meanMSB&10000000=0 then sign=0 else sign=01111111
                sign = (byte)( ( rxbuffer[1] & signMask) !=0 ? 0xff : 0x00);
                meanByte[3] = sign;
                meanByte[2] = sign;
                meanByte[1] = rxbuffer[1];
                meanByte[0] = rxbuffer[0];
                // Convert to int using little endian format
                mean = ByteBuffer.wrap(meanByte).order(ByteOrder.LITTLE_ENDIAN).getInt(); 
                // Save mean values
                meanValues.add(mean);
                //if bMSB&10000000=0 then sign=0 else sign=01111111
                sign = (byte)( ( rxbuffer[3] & signMask) !=0 ? 0xff : 0x00);
                bByte[3] = sign;
                bByte[2] = sign;
                bByte[1] = rxbuffer[3];
                bByte[0] = rxbuffer[2];
                // Convert to int using little endian format
                b = ByteBuffer.wrap(bByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
                // Save b values
                bValues.add(b);

                // Rest 128 bytes are the differential pairs 
                for(int j=4; j<132; j++) {
                    rx = rxbuffer[j];
                    // Use mask 00001111 to catch first 4-bits or nibble 1
                    nibble1 = (int)(rx & mask1); 
                    // Use mask 11110000 to catch last 4-bits or nibble 2
                    nibble2 = (int)((rx & mask2)>>4); 
                    // Subtract 8 from first nibble
                    diff1 = nibble1-8;   
                    differences.add(diff1);
                    // Multiply difference 1 with quantist b
                    diff1 = diff1*b;
                    // Subtract 8 from second nibble
                    diff2 = nibble2-8;
                    differences.add(diff2);
                    // Multiply difference 2 with quantist b
                    diff2 = diff2*b;
                    // First sound sample
                    x1 = diff1 + temp + mean;
                    soundSamples.add(x1);
                    // Second sound sample
                    x2 = diff1 + diff2 + mean;
                    soundSamples.add(x2);
                    temp = diff1;
                    // Add values and update counter
                    song[counter] = (byte)(x1 & 0x000000FF);
                    song[counter + 1] = (byte)((x1 & 0x0000FF00)>>8);
                    song[counter + 2] = (byte)(x2 & 0x000000FF);
                    song[counter + 3] = (byte)((x2 & 0x0000FF00)>>8);
                    counter+=4;
                }
            } catch (Exception ex) {    
                System.out.println("[AUDIO]" + ex);
            }
        }
        // If song mode is selected, play the received song
        System.out.println("Playing the song");
        AudioFormat aqpcmFormat = new AudioFormat(8000,16,1,true,false);
        try{
            SourceDataLine playsong = AudioSystem.getSourceDataLine(aqpcmFormat);
            playsong.open(aqpcmFormat,32000);
            playsong.start();
            playsong.write(song,0,256*2*totalPackets);
            playsong.stop();
            playsong.close();
        } catch (Exception ex) {
            System.out.println("[AUDIO]" + ex);
        }

        // Close communication sockets
        try{
            sendSocket.close();
        }catch(Exception ex){
            System.out.println("[AUDIO]Error in closing the Send socket\n" + ex);
        }
        try{
            receiveSocket.close();
        }catch(Exception ex){
            System.out.println("[AUDIO]Error in closing the Receive socket\n" + ex);
        }
        BufferedWriter bw;
        // Save difference pairs 
        bw = null;
        try {
            File file = null;
            file = new File(path+"/"+"AQDPCM_DIFFS_SONG_" + songCode + ".csv");
            
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write("differences");
            bw.newLine();
            // Write differences to a txt file
            for(int i = 0 ; i < differences.size() ; i ++){
                bw.write(differences.get(i).toString());
                bw.newLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
            }
        }
         // Save received sound to a .csv
         bw = null;
         try {
            File file = null;
            file = new File(path+"/"+"AQDPCM_SAMPLES_SONG_" + songCode + ".csv");

             bw = new BufferedWriter( new FileWriter(file, false));
             if (!file.exists()){
                 file.createNewFile();
             }
            bw.write("samples");
            bw.newLine();   
             // Write differences to a csv file
             for(int i = 0 ; i < soundSamples.size() ; i ++){
                 bw.write(soundSamples.get(i).toString());
                 bw.newLine();
             }
         }catch(IOException ioe){
             ioe.printStackTrace();
         }
         finally{
             try{
                 if(bw != null) bw.close();
             }catch(Exception ex){
                 System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
             }
         }
         try {
            File file = null;
            file = new File(path+"/"+"AQDPCM_MEANS_SONG_" + songCode + ".csv");
            
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write("means");
            bw.newLine();
            // Write differences to a csv file
            for(int i = 0 ; i < meanValues.size() ; i ++){
                bw.write(meanValues.get(i).toString());
                bw.newLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
            }
        }
        try {
            File file = null;
            file = new File(path+"/"+"AQDPCM_STEPS_SONG_" + songCode + ".csv");
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write("steps");
            bw.newLine();
            // Write differences to a csv file
            for(int i = 0 ; i < bValues.size() ; i ++){
                bw.write(bValues.get(i).toString());
                bw.newLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[AUDIO]Error in closing the BufferedWriter" + ex);
            }
        }

    }

    /* 
    ? This function begins the ithakicopter.jar process and receives 
    ? packets containing copter information
    */
    private static void ithakiCopter(String copterRequestCode, int clientPort, byte[] hostIP, int mode)
    throws IOException {
        // Execute ithakicopter.jar
        Process copter = Runtime.getRuntime().exec("java -jar ithakicopter.jar");
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception ex){
            System.out.println("[COPTER SLEEP]"+ex);
        }
        String msg = "";
        ArrayList<String> messages = new ArrayList<String>();
        final byte[] txbuffer = copterRequestCode.getBytes();
        // Socket used to send packets to server
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,48078);
        // Socket used to receive packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        // Timeout 
        receiveSocket.setSoTimeout(5000);
        // Buffer for server's response
        byte[] rxbuffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Receive 30 messages
        for (int i = 0; i < 30; i++){
            try{
                receiveSocket.receive(receivePacket);
                msg = new String(rxbuffer,0,receivePacket.getLength());
                messages.add(msg);
                System.out.println(msg);
            }catch(Exception ex){
              System.out.println("[COPTER]" + ex);
            }
        }

        BufferedWriter bw;
        // Save communication times to a file named copter.txt
        bw = null;
        try {
            File file = new File(path+"/"+"COPTER" + mode + ".csv");
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write("copter");
            bw.newLine();
            for(int i=0; i<messages.size(); i++)
            {
                bw.write(String.valueOf(messages.get(i)));
                bw.newLine();
            }
            bw.newLine();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[COPTER]Error in closing the BufferedWriter" + ex);
            }
        }
        receiveSocket.close();
      
        // Kill ithakicopter process
        copter.destroy();
    }
    
    /* 
    ? This function receives packets containing vehicle diagnostics from the server.
    * PID 1F: Engine run time info
    * PID 0F: Ithake air temperature
    * PID 11: Throttle position info
    * PID 0C: Engine RPM
    * PID 0D: Vehicle speed
    * PID 05: Coolant temperature
    */
    private static void onBoardDiagnostics(String vehicleRequestCode, int serverPort, int clientPort, byte[] hostIP, String pid) 
    throws IOException {
        // Server's response
        String msg ="";
	    // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer;
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        // final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        // hostAddress,serverPort);
        // Socket used to receive packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        // Timeout 
        receiveSocket.setSoTimeout(3000);
        // Buffer for server's response
        byte[] rxbuffer = new byte[5000];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Indexes used for while loop termination
        double start = 0, end = 0, sessionTime = 30*1000;
        start = System.nanoTime();
        vehicleRequestCode = vehicleRequestCode + "OBD=01 " + pid;
        txbuffer = vehicleRequestCode.getBytes();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        String nibble1, nibble2;
        int XX = 0, YY = 0;
        int[] values = new int[25000*2]; 
        ArrayList<Double> OBDvalues = new ArrayList<>();
        int counter = 0;
        while(end < sessionTime)
        {
            // Send packet to server
            try {
                sendSocket.send(sendPacket);
            } catch (final Exception x) {
                System.err.println("[VEHICLE]" + x);
            }
            // Receive response packet from server
            try {
                receiveSocket.receive(receivePacket);
                msg = new String(rxbuffer,0,receivePacket.getLength());
                if (msg.length() == 11) {
                    nibble1 = msg.substring(6,8);
                    nibble2 = msg.substring(9,11);
                    XX = Integer.parseInt(nibble1, 16);
                    YY = Integer.parseInt(nibble2, 16);
                } else if(msg.length() == 8) {
                    nibble1 = msg.substring(6,8);
                    XX = Integer.parseInt(nibble1, 16);
                }
                // Engine runtime
                if (pid == "1F") {
                    OBDvalues.add((double) (256 * XX + YY));
                    System.out.println("Engine runtime: " + OBDvalues.get(counter));
                }
                // Intake air temperature
                else if (pid == "0F") {
                    OBDvalues.add((double) (XX - 40));
                    System.out.println("Ithaki air temperature: " + OBDvalues.get(counter));
                }
                // Throttle position
                else if (pid == "11") {
                    OBDvalues.add((double) (XX * 100 / 255));
                    System.out.println("Throttle position: " + OBDvalues.get(counter));
                }
                // Engine RPM
                else if (pid == "0C") {
                    OBDvalues.add((double) (((XX * 256) + YY)/4));
                    System.out.println("Engine RPM: " + OBDvalues.get(counter));
                }
                // Vehicle speed
                else if (pid == "0D") {
                    OBDvalues.add((double) (XX));
                    System.out.println("Vehicle Speed: " + OBDvalues.get(counter));
                }
                //Coolant temperature
                else if (pid == "05") {
                    OBDvalues.add((double) (XX - 40));
                    System.out.println("Coolant temperature: " + OBDvalues.get(counter));
                }
                counter++;
            } catch (final Exception x) {
                System.err.println("[VEHICLE]" + x);
            }
            end = (System.nanoTime() - start) / 1000000;
        }
        try {
            sendSocket.close();
            receiveSocket.close();                
        } catch (Exception ex) {
            System.out.println("[VEHICLE] Error closing sockets" + ex);
        }
        File file = null;
        // Engine runtime
        if (pid == "1F") {
            file = new File(path + "/" + "engineRuntime.csv");
        }
        // Intake air temperature
        else if (pid == "0F") {
            file = new File(path + "/" + "airTemperature.csv");
        }
        // Throttle position
        else if (pid == "11") {
            file = new File(path + "/" + "throttlePosition.csv");
        }
        // Engine RPM
        else if (pid == "0C") {
            file = new File(path + "/" + "engineRPM.csv");
        }
        // Vehicle speed
        else if (pid == "0D") {
            file = new File(path + "/" + "vehicleSpeed.csv");
        }
        //Coolant temperature
        else if (pid == "05") {
            file = new File(path + "/" + "coolantTemperature.csv");
        }
        FileOutputStream stream = new FileOutputStream(file);
        // Write Results to Files //
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            bw.write(file.toString().replace(path, "").replace("/","").replace(".csv",""));
            bw.newLine();

            for(int i=0; i<OBDvalues.size(); i++)
            {
                bw.write(String.valueOf(OBDvalues.get(i)));
                bw.newLine();
            }
        } catch (IOException x) {
            System.out.println("[VEHICLE]Failure, writing results of Vehicle Operation.");
        }finally{
            try{
                if(bw != null) bw.close();
            }catch(Exception ex){
                System.out.println("[VEHICLE]Error in closing the BufferedWriter" + ex);
            }
        }

    }

    /*
    ? This function sends empty packets to the server.
    ? This process is performed in order to facilitate the observation of the packets at wireshark tool
    */
    private static void sendVoid(int serverPort, int clientPort, byte[] hostIP)
    throws IOException {
        // Server's IP address
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = "0".getBytes();
        // Socket used to send packets to server
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);
        for(int i=0; i<10; i++) {
            try {
                sendSocket.send(sendPacket);
            } catch (final Exception x) {
                System.err.println("[ECHO]" + x);
            }
        }
    }
        
}

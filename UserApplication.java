import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/*
    @author Zikopis Evangelos
    ? Computer Networks Assignment, 2020
    TODO: Packet Communication, Receive Image, Receive Audio, Copter Communication, Vehile Diagnostics
*/
public class UserApplication {
    private static final String echoRequestCode = "E5310";
    private static final String imageRequestCode = "M0079";
    private static final String audioRequestCode = "A9184";
    private static final String copterRequestCode = "Q5367";
    private static final String vehicleRequestCode = "V0631";
    private static final int serverPort = 38021;
    private static final int clientPort = 48021;
    private static final byte[] hostIP = { (byte)155,(byte)207,18,(byte)208 };

    public static void main(final String[] args) throws IOException {
       
        System.out.println("The Session has started");
        // Call echoPackages mode with delay
        // echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 1);
        // Call echoPackages mode without delay
        // echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 2);
        // Call echoPackages mode with temperatures mode 
        // echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 3);
        // Call imageCapture with mode 1
        // imageCapture(imageRequestCode, clientPort, serverPort, hostIP, 1);
        // Call imageCapture with mode 2
        // imageCapture(imageRequestCode, clientPort, serverPort, hostIP, 2);
        // dpcmSound(audioRequestCode, serverPort, clientPort, hostIP, 1);
        // aqdpcmSound(audioRequestCode, serverPort, clientPort, hostIP, 1);
        ithakiCopter(copterRequestCode, 48078, hostIP, 1);

    }

    private static void echoPackages(final int clientPort, final int serverPort, String echoRequestCode, byte[] hostIP, int echoMode)
    throws IOException {
        // Check mode
        if (echoMode == 1) {
            // Delay Mode
            System.out.println("Echo Delay Mode "+echoRequestCode);
            echoRequestCode = echoRequestCode+"\r";
        } else if (echoMode == 2) {
            // Non Delay Mode
            System.out.println("Echo non-Delay Mode");
            echoRequestCode = "E0000\r";
        } else {
            // Temperature Mode
            System.out.println("Temperature Delay Mode");
            echoRequestCode = echoRequestCode+"T00\r";
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
        receiveSocket.setSoTimeout(3000);
        // Buffer for server's response
        byte[] rxbuffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);
        // Indexes used for while loop termination
        double start = 0, end = 0, sessionTime = 10*1000;
        // Indexes used to messure communication time
        double sendTime = 0, recTime = 0;
        ArrayList<Double> receiveTimes = new ArrayList<Double>();
        // Packet counter
        int packetCounter = 0;
        // Initialize start index
        start = System.nanoTime();
        int tempCounter = 0;
        int errors = 0;
        while(tempCounter < 5)
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
            } catch (final Exception x) {
                System.err.println("[ECHO]" + x);
                errors++;
            }
            end = (System.nanoTime() - start) / 1000000;
            tempCounter++;
        }
        double avg = 0;
        // Total received packets 
        packetCounter = receiveTimes.size();
        if (packetCounter != 0) {
            avg = sessionTime / packetCounter;
        }
        // Print results
        System.out.println("Errors: " + String.valueOf(errors) + "/" +  String.valueOf(tempCounter));
        System.out.println("Total packets received: " + String.valueOf(packetCounter));
        System.out.println("Average time per packet: " + String.valueOf(avg) + " Milliseconds");
        System.out.println("Communication Time: " + String.valueOf(sessionTime/(60*1000)) + " Minute");
        if (echoMode == 2) {
            System.out.println("Average Temperature at station T00: 0 Celcius");
        }
        BufferedWriter bw;
        // Save communication times to a file named "echoRequestCode".txt
        if (echoMode!=3) {
            bw = null;
            try {
                File file = new File(echoRequestCode+".csv");
                bw = new BufferedWriter( new FileWriter((echoRequestCode+".csv"), false));
                if (!file.exists()){
                    file.createNewFile();
                }
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
                File file = new File("R"+echoRequestCode+".csv");
                bw = new BufferedWriter( new FileWriter(("R"+echoRequestCode+".csv"), false));
                if (!file.exists()){
                    file.createNewFile();
                }
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
        FileOutputStream imageFile = new FileOutputStream(imageRequestCode+".jpeg");
        // Timeout 
        receiveSocket.setSoTimeout(3600);
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

    private static void dpcmSound(String audioRequestCode, int serverPort, int clientPort, byte[] hostIP, int audioMode)
    throws IOException {
        // Total datagram sound packets that will be received
        int totalPackets = 500;
        int mask1 = 15,mask2 = 240;
        int b = 5,rx;
        int nibble1,nibble2;
        int diff1,diff2;
        int x1 = 0,x2 = 0;
        int counter = 0;
        ArrayList<Integer> differences = new ArrayList<Integer>();
        ArrayList<Integer> soundSamples = new ArrayList<Integer>();
        if (audioMode == 1) {
            audioRequestCode = audioRequestCode + "F" + totalPackets; 
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
                    // Update counter
                    counter += 2;
                }
            } catch (Exception ex) {    
                System.out.println("[AUDIO]" + ex);
            }
            // if((i%100)==0){
            //     System.out.println((totalPackets-i)+" samples left");
            //   }
            
        }
        // If song mode is selected, play the received song
        if(audioMode==1){
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
        }

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

        BufferedWriter bw;
        // Save difference pairs to a file named DIFFS+audioCode+Mode.txt
        bw = null;
        try {
            File file = new File("DIFFS"+audioRequestCode+"MODE"+audioMode+".txt");
            bw = new BufferedWriter( new FileWriter(file, false));
            if (!file.exists()){
                file.createNewFile();
            }
            // Write differences to a txt file
            for(int i = 0 ; i < differences.size() ; i += 2){
                bw.write("" + differences.get(i) + " " + differences.get(i+1));
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

         // Save difference pairs to a file named DIFFS+audioCode+Mode.txt
         bw = null;
         try {
             File file = new File("AUDIO"+audioRequestCode+"MODE"+audioMode+".txt");
             bw = new BufferedWriter( new FileWriter(file, false));
             if (!file.exists()){
                 file.createNewFile();
             }
             // Write differences to a txt file
             for(int i = 0 ; i < soundSamples.size() ; i += 2){
                 bw.write("" + soundSamples.get(i) + " " + soundSamples.get(i+1));
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

    private static void aqdpcmSound(String audioRequestCode, int serverPort, int clientPort, byte[] hostIP, int audioMode)
    throws IOException {
        // Total datagram sound packets that will be received
        int totalPackets = 200;
        int mask1 = 15,mask2 = 240;
        int mean, b, rx;
        int nibble1,nibble2;
        int diff1,diff2;
        int x1 = 0,x2 = 0;
        int counter = 0;
        int temp = 0;
        int signMask = 0x80;
        ArrayList<Integer> meanValues = new ArrayList<Integer>();
        ArrayList<Integer> bValues = new ArrayList<Integer>();
        ArrayList<Integer> differences = new ArrayList<Integer>();
        ArrayList<Integer> soundSamples = new ArrayList<Integer>();
        
        if (audioMode == 1) {
            audioRequestCode = audioRequestCode + "AQF" + totalPackets; 
        } else if (audioMode == 2) {
            audioRequestCode = audioRequestCode + "AQT" + totalPackets; 
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
                    // Construct audio-song
                    song[counter] = (byte)x1;
                    song[counter + 1] = (byte)x2;
                    // Update counter
                    
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
        if(audioMode==1){
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
        }

    }

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
        // Socket used to receive packets from server
        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        // Timeout 
        receiveSocket.setSoTimeout(5000);
        // Buffer for server's response
        byte[] rxbuffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(rxbuffer,rxbuffer.length);

        for (int i = 0; i < 5; i++){
            try{
                receiveSocket.receive(receivePacket);
                msg = new String(rxbuffer,0,receivePacket.getLength());
                System.out.println(msg);
            }catch(Exception ex){
              System.out.println("[COPTER]" + ex);
            }
        }
        receiveSocket.close();
        // Kill ithakicopter process
        copter.destroy();
    }
    
}

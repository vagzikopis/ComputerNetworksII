import java.io.*;
import java.net.*;
import java.util.ArrayList;


/*
    @author Zikopis Evangelos
    ? Computer Networks Assignment, 2020
    TODO: Packet Communication, Receive Image, Receive Audio, Copter Communication, Vehile Diagnostics
*/
public class UserApplication{
    private static final String echoRequestCode = "E0712";
    private static final String imageRequestCode = "M6611";
    private static final int serverPort = 38019;
    private static final int clientPort = 48019;
    private static final byte[] hostIP = { (byte)155,(byte)207,18,(byte)208 };

    public static void main(final String[] args) throws IOException {
       
        System.out.println("The Session has started\n");
        // Call echoPackages mode with delay
        echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 0);
        // Call echoPackages mode without delay
        echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 1);
        // Call echoPackages mode with temperatures mode 
        // echoPackages(clientPort, serverPort, echoRequestCode, hostIP, 2);
    }

    private static void echoPackages(final int clientPort, final int serverPort, String echoRequestCode, byte[] hostIP, int echoMode)
    throws IOException {
        // Check mode
        if (echoMode == 0) {
            // Delay Mode
            System.out.println("Echo Delay Mode");
            echoRequestCode = echoRequestCode+"\r";
        } else if (echoMode == 1) {
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
        receiveSocket.setSoTimeout(3600);
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
        System.out.println("Total packets received: " + String.valueOf(packetCounter));
        System.out.println("Average time per packet: " + String.valueOf(avg) + " Milliseconds");
        System.out.println("Communication Time: " + String.valueOf(sessionTime/(60*1000)) + " Minute");
        if (echoMode == 2) {
            System.out.println("Average Temperature at station T00: 0 Celcius");
        }
        BufferedWriter bw;
        // Save communication times to a file named "echoRequestCode".txt
        if (echoMode!=2) {
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
        if (echoMode!=2) {
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

}

  

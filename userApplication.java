import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
    @author Zikopis Evangelos
    ? Computer Networks Assignment, 2020
    TODO: Packet Communication, Receive Image, Receive Audio, Copter Communication, Vehile Diagnostics
*/
public class userApplication{
    private static final String echoRequestCode =  "E1696\r";
    private static final int serverPort = 38023;
    private static final int clientPort = 48023;
    private static final byte[] hostIP = { (byte)155,(byte)207,18,(byte)208 };

    public static void main(final String[] args) throws IOException {
       
        System.out.println("The Session has started\n");
        echoPackages(clientPort, serverPort, echoRequestCode, hostIP);

    }


    private static void echoPackages(final int clientPort, final int serverPort, String echoRequestCode, byte[] hostIP) throws IOException {
        
        String message ="";
        final InetAddress hostAddress = InetAddress.getByAddress(hostIP);
        final byte[] txbuffer = echoRequestCode.getBytes();
        final DatagramSocket sendSocket = new DatagramSocket();
        final DatagramPacket sendPacket = new DatagramPacket(txbuffer,txbuffer.length,
        hostAddress,serverPort);

        final DatagramSocket receiveSocket = new DatagramSocket(clientPort);
        receiveSocket.setSoTimeout(3600);
        byte[] rxbuffer = new byte[2048];

        try {
            sendSocket.send(sendPacket);
            System.out.println("... Send Ok ... \n");
        } catch (final Exception x) {
            System.err.println(x);
        }

        final DatagramPacket receivePacket = new DatagramPacket(rxbuffer, rxbuffer.length);
        try {
            receiveSocket.receive(receivePacket);
            message = new String(rxbuffer, 0, receivePacket.getLength());
            System.out.println(message);
        } catch (final Exception x) {
            System.err.println(x);
        }
        
        finally{
            try{
                sendSocket.close();
            }catch(Exception ex){
              System.out.println("Error in closing the Send socket\n" + ex);
            }
            try{
                receiveSocket.close();
              }catch(Exception ex){
                System.out.println("Error in closing the Receive socket\n" + ex);
              }
        }
    }

}
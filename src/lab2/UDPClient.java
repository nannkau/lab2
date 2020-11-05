package lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static int destPort = 1234;
    public static String hostname = "localhost";
    public static void client() {
        DatagramSocket socket;
        DatagramPacket dpsend, dpreceive;
        InetAddress add; Scanner stdIn;
        try {
            add = InetAddress.getByName(hostname);	//UnknownHostException
            socket = new DatagramSocket();			//SocketException
            stdIn = new Scanner(System.in);
            while(true) {
                System.out.print("Client input: ");
                String tmp = stdIn.nextLine();
                byte[] data = tmp.getBytes();
                dpsend = new DatagramPacket(data, data.length, add, destPort);
                System.out.println("Client sent " + tmp + " to " + add.getHostAddress() +
                        " from port " + socket.getLocalPort());
                socket.send(dpsend);				//IOExeption
                if(tmp.equals("bye")) {
                    System.out.println("Client socket closed");
                    stdIn.close();
                    socket.close();
                    break;
                }
                // Get response from server
                dpreceive = new DatagramPacket(new byte[100000], 100000);
                socket.receive(dpreceive);
                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Client get: " + tmp + " from server");
            }
        } catch (IOException e) { System.err.println(e);}
    }
}

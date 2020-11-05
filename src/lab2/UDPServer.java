package lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static int buffsize = 512;
    public static void server(Integer port,Service service ) {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        try {
            socket = new DatagramSocket(port);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while(true) {
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0 , dpreceive.getLength()).trim().replaceAll(" ", "%20");
                System.out.println("Server received: " + tmp + " from " +
                        dpreceive.getAddress().getHostAddress() + " at port " +
                        socket.getLocalPort());
                if(tmp.equals("bye")) {
                    System.out.println("Server socket closed");
                    socket.close();
                    break;
                }
                String data=service.getData(tmp);
                dpsend = new DatagramPacket(data.getBytes(), data.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + data + " to client");
                socket.send(dpsend);
            }
        } catch (IOException e) { System.err.println(e);}
    }
}

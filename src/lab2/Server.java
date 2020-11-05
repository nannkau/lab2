package lab2;

public class Server {
    public static void main(String[] args){
        Service service= new Service();
        UDPServer.server(1234,service);
    }
}

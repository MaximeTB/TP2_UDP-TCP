import java.lang.reflect.Executable;
import java.net.*;

public class UDPServer {
    private static final int defaultport=8080;
    private InetAddress localAdress;
    private int port;
    private DatagramSocket udpSocket;


    public UDPServer() throws Exception { //default port value
        this.port=defaultport;
        localAdress=InetAddress.getLocalHost();
        this.udpSocket=new DatagramSocket(this.port);
    }

    public UDPServer(int port) throws Exception{
        this.port=port;
        this.udpSocket= new DatagramSocket(this.port,localAdress);
    }


    void launch() throws Exception{
        System.out.println("--Running Server at " + localAdress+"--"+ this.port);
        String msg;

        byte[] buf = new byte[1024];
        DatagramPacket receivedPacket = null;
        DatagramPacket sentPacket = null ;
                //new DatagramPacket(buf,1024);

        while(true){

            receivedPacket = new DatagramPacket(buf,buf.length);

            this.udpSocket.receive(receivedPacket);
            msg= new String(receivedPacket.getData()).trim();

            /*sentPacket = new DatagramPacket(buf,buf.length ,receivedPacket.getAddress(), receivedPacket.getPort());
            this.udpSocket.send(sentPacket);
            */
            buf= new byte[1024];

            System.out.println("Message Received from "+ receivedPacket.getAddress().getHostAddress()+ ":"+ msg);
            }
        }




    public static void main(String[] args) throws Exception{


        if(args.length != 0 ) {
            int port = Integer.parseInt(args[0]);
            UDPServer Server = new UDPServer(port);
            Server.launch();

        }else{
            UDPServer Server = new UDPServer();
            Server.launch();
        }
    }

}

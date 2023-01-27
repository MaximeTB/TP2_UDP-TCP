import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    private static final int defaultport=8080;
    private int servPort;
    private InetAddress serverAdress;
    private String serverName;

    public UDPClient (String serverName, int servPort) throws Exception {
        this.servPort = servPort;
        this.serverName = serverName;
        this.serverAdress= InetAddress.getByName(serverName);
    }

    /*public UDPClient (String serverName) throws Exception {
        this.servPort = defaultport;
        this.serverName = serverName;
        this.serverAdress= InetAddress.getByName(serverName);
    }


    public UDPClient(byte[] serverIP,int servPort) throws Exception{
        this.servPort= servPort;
        this.serverAdress = InetAddress.getByAddress(serverIP);
        this.serverName= this.serverAdress.getHostName();
    }
    public UDPClient(byte[] serverIP) throws Exception{
        this.servPort= defaultport;
        this.serverAdress = InetAddress.getByAddress(serverIP);
        this.serverName= this.serverAdress.getHostName();
    }*/


    public void sendData(byte[] data,String stringData) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Socket Ouverte");
        DatagramPacket message=new DatagramPacket(data,data.length,this.serverAdress,this.servPort);
        socket.send(message);
        System.out.println("Message Envoyé au Server : "+stringData );
        socket.close();
    }

    public static void main(String[] args) throws Exception{
        //If the user put -ip as option, we use the constructor that take IP of the server
        //Else we use the constructor that use Server name.
        boolean keeprunning=true;
        UDPClient client = new UDPClient(args[0],Integer.parseInt(args[1]));
        System.out.println("Client settled");
        Scanner sc = new Scanner(System.in);
        String stringData;

        while(keeprunning){
            byte[] data;
            stringData=sc.nextLine();
            if (stringData.equals("exit")){
                keeprunning=false;
                System.out.println("Shutting the Client");
            }else{
                data = stringData.getBytes();
                client.sendData(data,stringData);
            }

        }
    }

}

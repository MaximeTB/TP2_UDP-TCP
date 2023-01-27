import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int defaultport=8080;
    private InetAddress localAdress;
    private int port;


    public TCPServer(int port) throws Exception{
        this.port = port;
        this.localAdress=InetAddress.getLocalHost();
    }
    public TCPServer() throws Exception{
        this.port = defaultport;
        this.localAdress=InetAddress.getLocalHost();
    }

    public void launch() throws Exception{
        ServerSocket serverSckt = new ServerSocket(this.port);
        System.out.println("Server Adress : " + this.localAdress.toString() + '\n' + "Listenning port : " + this.port+'\n');
        while(true) {
            Socket clientSckt = serverSckt.accept();
            System.out.println("Client "+clientSckt.getRemoteSocketAddress()+" connected \n");


            InputStreamReader input = new InputStreamReader(clientSckt.getInputStream());
            OutputStreamWriter output = new OutputStreamWriter(clientSckt.getOutputStream());

            BufferedReader inputReader = new BufferedReader(input);
            BufferedWriter outputWriter = new BufferedWriter(output);

            output.write("You are connected to the server "+this.localAdress+'\n'+"Press q to exit.\n");
            output.flush();



            System.out.println("Message from the Client :" +inputReader.readLine());


            String echo;

            while (true) {
                echo = inputReader.readLine();
                if (echo.equals("q")){
                    System.out.println("Client "+clientSckt.getRemoteSocketAddress()+" quit the server");
                    output.write("Goodbye");
                    output.flush();
                    break;
                }else {
                    echo = echo +'\n';
                    System.out.println("Client " + clientSckt.getRemoteSocketAddress()+ " send :" + echo);
                    output.write(echo);
                    output.flush();
                }
            }
            inputReader.close();
            outputWriter.close();

            input.close();
            output.close();

            clientSckt.close();
        }
    }

    public static void main(String[] args) throws Exception {
        TCPServer Server = new TCPServer();
        Server.launch();
    }
}
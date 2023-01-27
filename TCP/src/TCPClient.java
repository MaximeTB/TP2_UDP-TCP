import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    private static final int defaultport=8080;
    private InetAddress serverAdress;
    private int port;
    private String host;

    private InputStreamReader input;
    private OutputStreamWriter output;

    private BufferedReader inputReader;
    private PrintWriter outputWriter;

    private Socket sckt;


    public TCPClient(String host) {
        this.host = host;
        this.port= defaultport;
        try{
        this.serverAdress = InetAddress.getByName(host);}
        catch (UnknownHostException e){
            System.out.println("Server not found :" + e.getMessage());
        }
    }
    public TCPClient(String host,int port) {
        this.host=host;
        this.port=port;
        try {
            this.serverAdress= InetAddress.getByName(host);
        }catch(UnknownHostException e){
            System.out.println("Server not found :" + e.getMessage());
        };
    }

    public void ConnectToServer(){ //Create the Socket to the Server and all the input and ouput Streams
        String message;
        try{
            this.sckt= new Socket(this.serverAdress,this.port);

            this.input= new InputStreamReader(sckt.getInputStream());
            this.output = new OutputStreamWriter(sckt.getOutputStream());

            this.inputReader = new BufferedReader(input);
            this.outputWriter = new PrintWriter(output,true);

        }catch(IOException ex){
            System.out.println("I/O Problem :" + ex.getMessage());
        };

        try{
            message=this.inputReader.readLine();
            System.out.println(message);
            message=this.inputReader.readLine();
            System.out.println(message);

            this.outputWriter.println("Message Sending Test");


        }


        catch(Exception e){
            System.out.println("Error while receiving message from server:" + e.getMessage());}
    }


    public void Close(){
        try {
            this.inputReader.close();

            this.outputWriter.close();

            this.input.close();
            this.output.close();

            //this.sckt.close();

        }catch(IOException e){
            System.out.println("Socket closing Error :" + e.getMessage());
        }
        System.out.println("Client closed");
    }



    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        TCPClient client;
        Boolean keeprunning = true;
        String messageToServer;
        String messageFromServer;

        Scanner sc = new Scanner(System.in);

        if (args.length==2){
            client = new TCPClient(args[0],Integer.parseInt(args[1]));
        }else if (args.length==1){
            client = new TCPClient(args [0]);
        }else{
            throw new Error("Invalid number of argument");
        }
        client.ConnectToServer();

        while(keeprunning){
            messageToServer = sc.nextLine();
            if(messageToServer.toString().equals("q")){
                client.outputWriter.println(messageToServer);
                keeprunning=false;
                System.out.println("Closing client");
            }else{
                    client.outputWriter.println(messageToServer);
                try {
                    messageFromServer = client.inputReader.readLine();
                    System.out.println("From the Server :" + messageFromServer.toString());
                } catch (Exception e) {
                    System.out.println("Error while receiving message from server:" + e.getMessage());
                }
            }
        }
           //client.Close();
    }
}

package com.example.turlough.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by turlough on 15/07/16.
 */
public class SocketUtils {

    private final static int port = 6666;
    private final static String name = "localhost";
    ServerSocket serverSocket;
    PrintWriter out;
    BufferedReader in;
    Socket client;
    int timeout;

    public SocketUtils(int timeout) {
        this.timeout = timeout;

    }

    public void createServer( )throws IOException {

        System.out.println("Server: Creating server socket");
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeout);

        while (true) {
            System.out.println("Server: waiting for client");

            System.out.println("Server: accepted from client");
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            in.lines().forEach((line) -> {
                System.out.println(line);
                out.printf("Server: Thanks for '%s'", line);
            });
        }


    }

    public void createClient() throws IOException{

        System.out.println("Client: Connecting to " + name + " on port " + port);

        while (true) {
            sleep(50);
            System.out.println("Client: waiting");
            client = serverSocket.accept();
            if(client == null) {
                System.out.println("Client: client is null");
            }
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from "+ client.getLocalSocketAddress());

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Client: Server says " + in.readUTF());
        }

    }

    private void sleep(long millis){
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

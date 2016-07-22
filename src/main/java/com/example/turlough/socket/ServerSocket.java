package com.example.turlough.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocket extends Thread {

    private java.net.ServerSocket serverSocket;
    Socket server;
    DataInputStream in;
    DataOutputStream out;



    public void open(int port) throws IOException {
        serverSocket = new java.net.ServerSocket(port);
        serverSocket.setSoTimeout(30000);


    }

    public void run() {
        do {
            try {

                System.out.println("Server: Waiting for client on port " + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();
                System.out.println("Server: Connected to " + server.getRemoteSocketAddress());
                in = new DataInputStream(server.getInputStream());
                out = new DataOutputStream(server.getOutputStream());

                String msg = in.readUTF();
                System.out.printf("Server: received '%s'", msg);
                out.writeUTF("Server: received message: '" + msg + "'");

            } catch (SocketTimeoutException s) {
                System.out.println("Server: Socket timed out!");

            } catch (IOException e) {
                System.out.println(e.getMessage());

            }
        } while(true);

//        System.out.println("Server: closed");
    }

    public void close(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        try {

            ServerSocket server = new ServerSocket();
            server.open(port);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.example.turlough.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocket extends Thread {
    private java.net.ServerSocket serverSocket;


    public ServerSocket(int port) throws IOException {
        serverSocket = new java.net.ServerSocket(port);
        serverSocket.setSoTimeout(30000);
    }

    public void run() {
        do {
            try {

                System.out.println("Server: Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Server: Connected to " + server.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(server.getInputStream());
                String msg = in.readUTF();
                System.out.printf("Server: received '%s'", msg);
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Server: received message: '" + msg + "'");


            } catch (SocketTimeoutException s) {
                System.out.println("Server: Socket timed out!");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        } while(! serverSocket.isClosed());

        System.out.println("Server: closed");
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
            Thread t = new ServerSocket(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
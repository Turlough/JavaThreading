package com.example.turlough.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {

    Socket client;

    public static void main(String[] args) {

        new ClientSocket().open("localhost", 6666);
    }

    public String enterMessage(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message: \t");
        String msg = scanner.nextLine();
        return sendMessage(msg);
    }

    public String sendMessage(String msg){
        try {
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            System.out.printf("\nClient: sending message '%s'\n", msg);
            out.writeUTF(msg);
            out.close();

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            String response = ("Client: Server says '" + in.readUTF() + "'");
            in.close();
            return response;

        } catch (IOException e) {
            return e.getMessage();
        }
    }


    public void open(String serverName, int port){

        try {

            System.out.println("\nClient: Connecting to " + serverName + " on port " + port);
            client = new Socket(serverName, port);
            System.out.println("Client: connected to "+ client.getRemoteSocketAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
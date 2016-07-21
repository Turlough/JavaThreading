package com.example.turlough.menu;

import com.example.turlough.socket.ClientSocket;
import com.example.turlough.socket.ServerSocket;

import java.io.IOException;

/**
 * Created by turlough on 21/07/16.
 */
public class MenuRunner {

    public static void main(String... args){

        Menu menu = new Menu();
        menu.add("Menu item 1", () -> System.out.println("item 1 executed"));
        menu.add("Menu item 2", () -> System.out.println("item 2 executed"));

        //factoial(3) does not implement Runnable directly,
        //but it is a method that can be run
        //In fact, any Functional Interface with a void parameterless method can be used
        menu.add("factorial 5", () -> factorial(5));
        menu.add("factorial 15", () -> factorial(15));
        menu.add("factorial 20", () -> factorial(20));

        menu.add("client socket", ()-> sendSocketMessage());

        menu.show();
    }

    static void factorial(int value){
        long total = 1;
        for (int i = 1; i < value + 1 ; i++) {

            total *= i;
            System.out.print(total + " ");
        }
        System.out.printf("\nFactorial %d = %d\n", value, total);
    }

    static void sendSocketMessage() {

        int port = 6672;
        try {

            ServerSocket server = new ServerSocket(port);
            ClientSocket client = new ClientSocket();
            server.start();
            client.open("localhost", port);

            String response = client.enterMessage();
            System.out.println(response);

            client.close();
            server.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}

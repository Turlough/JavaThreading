package com.example.turlough.menu;

/**
 * Created by turlough on 21/07/16.
 */
public class MenuRunner {

    public static void main(String... args){
        Menu menu = new Menu();
        menu.add("Menu item 1", () -> System.out.println("item 1 executed"));
        menu.add("Menu item 2", () -> System.out.println("item 2 executed"));
        menu.show();
    }

}

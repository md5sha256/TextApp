package com.gmail.andrewandy.textserver;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    static TextClient server;
    Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        try {
            server = new TextClient(9951);
        } catch (IOException ex) {
            System.out.println("Connection timed out.");
        }

    }

}
package com.gmail.andrewandy.textclient;

import com.gmail.andrewandy.textclient.util.Common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static TextClient client;
    static Logger logger = Logger.getLogger("Server");


    public static void main(String[] args) {
        try {
             client = new TextClient(9951);
        }
        catch (IOException ex) {
            Common.log(Level.INFO, Common.colourise("&aHello &bI am blue, &cI am now red too, &dand now pink!"));
        }

    }

}
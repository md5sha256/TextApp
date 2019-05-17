package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Main {

    static TextServer server;
    private static File currentDir;
    Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            server = TextServer.getInstance();
            server.setupServer(9951);
            server.start();
            updateDir();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Connection timed out.");
        }
    }

    private static void updateDir() {
        try {
            currentDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public File getCurrentDir() {
        return currentDir;
    }
}
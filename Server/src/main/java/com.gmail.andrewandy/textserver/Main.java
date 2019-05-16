package com.gmail.andrewandy.textserver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Main {

    static TextServer server;
    Logger logger = Logger.getLogger(Main.class.getName());
    private static File currentDir;


    public static void main(String[] args) {
        try {
            server = new TextServer(9951);
            updateDir();

        }
        catch (IOException ex) {
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
package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    static TextServer server;
    private static File currentDir;
    private static boolean run = true;

    public static void main(String[] args) {
            try {
                server = TextServer.getInstance();
                server.setupServer(9951);
                updateDir();

            } catch (IOException ex) {
                System.out.println("Connection timed out.");
            }
            Scanner scanner = new Scanner(System.in);
            while (run) {
                String request = scanner.next();

                switch (request.toLowerCase()) {
                    case "quit":
                        return;
                    case "exit":
                        run = false;
                    case "stop":
                        return;
                    default:
                        System.out.println(Common.colourise("&f------------------------------------"));
                        System.out.println(Common.colourise("&bThe current avaliable command are:"));
                        System.out.println(Common.colourise("&e   - \"Help\""));
                        System.out.println(Common.colourise("&f------------------------------------"));
                        break;
                }
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
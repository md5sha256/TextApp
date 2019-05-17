package com.gmail.andrewandy.textclient;

import com.gmail.andrewandy.textclient.util.Common;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static TextClient client;
    private static String req = "Welcome";

    public static void main(String[] args) {
        System.out.println(Common.colourise("&aWelcome to the TextClient! This is a very experimental version."));
        try {
            while (req != null) {
                System.out.println("Please enter a remote host to connect to.");
                Inet4Address remoteAddress = null;
                try {
                    Scanner scanner = new Scanner(System.in);
                    req = scanner.next();
                    switch (req.toLowerCase()) {
                        case "exit":
                            System.out.println(Common.colourise("&aThank you and goodbye."));
                            return;
                        case "help":
                            System.out.println(Common.colourise("&aCurrently this feature is disabled."));
                            continue;
                        default:
                            remoteAddress = (Inet4Address) Inet4Address.getByName(req);
                    }
                }
                catch (UnknownHostException ex) {
                    System.out.println(Common.colourise("&cInvalid hostname."));
                    ex.printStackTrace();
                    continue;
                }
                catch (IllegalArgumentException ex) {
                    System.out.println(Common.colourise("&bInvalid Port."));
                }
                if (remoteAddress == null) {
                    continue;
                }
                System.out.println(Common.colourise("&bPlease enter a port."));
                try {
                    String req = checkInput();
                    if (Integer.valueOf(req) > 66556 || Integer.valueOf(req) < 0) {
                        System.out.println("Invalid host");
                        continue;
                    }
                    client = new TextClient(remoteAddress, Integer.valueOf(req));
                } catch (IllegalArgumentException ex) {
                    System.out.println(Common.colourise("&cInvalid host.&r"));
                    continue;
                }
                System.out.println(Common.colourise("&dSuccessfully connected to: " + remoteAddress.getHostAddress()));

                Object remote = client.checkForMessage(1000);
                if (remote != null) {
                    if (remote.getClass().isAssignableFrom(String.class)) {
                        System.out.println(Common.colourise((String) remote));
                    } else {
                        System.out.println("&e[Remote] Sent a custom Object.");
                    }
                }
                req = checkInput();
                if (req != null) {
                    client.sendMessage(req);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String checkInput() throws IOException{
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        if (next.equalsIgnoreCase("null")) {
            System.out.println("Illegal Argument!");
            return null;
        }
        switch (next.toLowerCase()) {
            case "exit":
                System.out.println(Common.colourise("&aThank you and goodbye."));
                scanner.close();
                return null;
            case "help":
                System.out.println(Common.colourise("&aCurrently this feature is disabled."));
                scanner.close();
                break;
                default:
                    if (client != null) {
                        client.sendMessage(next);
                    }
                    scanner.close();
                    return next;
        }
        scanner.close();
        return null;
    }
}
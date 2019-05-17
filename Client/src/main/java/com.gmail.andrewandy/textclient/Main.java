package com.gmail.andrewandy.textclient;

import com.gmail.andrewandy.textclient.util.Common;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static TextClient client;
    static Logger logger = Logger.getLogger("Server");
    static boolean loggedon;
    static Inet4Address ip4;
    static UUID clientID = UUID.randomUUID();

    public static void main(String[] args) {

        System.out.println(Common.colourise("&fWelcome to the CLI version of the TextApp."));

        if (ip4 == null || !loggedon) {
            System.out.println("Please enter the server's IP address. (IPV4)");
            getIp4();
            System.out.println(Common.colourise("&aConnected."));
        }
        try {
            if (ip4 == null) {
                System.out.println(Common.colourise("No IP Specified. Program will now exit."));
                return;
            }
            client = new TextClient(9951, ip4);
            client.start();
            client.sendMessage(clientID.toString());
            checkInput();
            System.out.println(Common.colourise("&aGood bye!"));
        } catch (IOException ex) {
            ex.printStackTrace();
            if (client != null && client.isAlive()) {
                client.disconnect();
            }
            loggedon = false;
        }
        if (client != null && client.isAlive()) {
            client.disconnect();
        }

    }

    private static Inet4Address getIp4() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.next();
        try {
            ip4 = (Inet4Address) Inet4Address.getByName(string);
            return ip4;
        } catch (UnknownHostException ex) {
            System.out.println("Invalid IP address.");
            getIp4();
        }
        return null;
    }


    private static void checkInput() throws IOException{
        Scanner scanner = new Scanner(System.in);
        switch (scanner.next().toLowerCase()) {
            case "exit":
                return;
            case "help":
                System.out.println(Common.colourise("&bTo send a message, just type anything that is not \"help\" or \"exit\""));
                default:
                    client.sendMessage(scanner.next());
        }
    }
}
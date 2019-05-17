package com.gmail.andrewandy.textclient;


import com.gmail.andrewandy.textclient.util.Common;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;

public class TextClient extends Thread {

    private static Socket socket;
    private static int port;
    private static Inet4Address ip4;
    private static TextClient instance;
    private static boolean run = true;

    public TextClient(int targetPort, Inet4Address address) throws IOException {
        port = targetPort;
        ip4 = address;
        socket = new Socket(address, targetPort);
    }

    public static int getPort() {
        return port;
    }

    public static Inet4Address getIp4() {
        return ip4;
    }

    public void sendMessage(String... messages) throws IOException {
        if (socket == null) {
            throw new IllegalStateException("Server not initialised");
        }
        DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(messages);
        for (String string : messages) {
            System.out.println(Common.colourise("&e[Client]: " + string));
        }
        stream.close();
    }

    public void endProcess(boolean toEnd) {
        run = toEnd;
    }

    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            Common.log(Level.SEVERE, "Unable to disconnect from remote socket!");
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (run) {
            if (socket.isClosed()) {
                Common.log(Level.WARNING, "&eClosed socket...");
                return;
            }
            try {
                DataInputStream dataStream = new DataInputStream(socket.getInputStream());
                ObjectInputStream in = new ObjectInputStream(dataStream);
                Object obj = in.readObject();
                if (obj instanceof String) {
                    System.out.println(Common.colourise((String) obj));
                }
                System.out.println("Custom object was sent...");

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Common.log(Level.SEVERE, "&cClient and Server version Mis-Match!");
            }
        }
    }
}

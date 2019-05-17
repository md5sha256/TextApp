package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;


public class TextServer extends Thread {

    private static TextServer instance;
    private ServerSocket serverSocket;
    private int port = -1;

    private boolean run = true;

    private Set<UUID> connected = new HashSet<>();


    private TextServer() {

    }

    public static TextServer getInstance() {
        if (instance == null) {
            return new TextServer();
        }
        return instance;
    }


    public void setupServer(int port) throws IOException {
        if (this.port != -1) {
            Common.log(Level.WARNING, "&eAttempted to start the server when it was already started!");
            return;
        }
        this.port = port;
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        Common.log(Level.INFO, "&fStarted server on " + serverSocket.getInetAddress());
    }

    @Override
    public void run() {
        while (run) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream dataStream = new DataInputStream(socket.getInputStream());
                ObjectInputStream in = new ObjectInputStream(dataStream);
                Object obj = in.readObject();
                if (!obj.getClass().isAssignableFrom(String.class)) {
                    System.out.println("Custom object.");
                    continue;
                }
                System.out.println(Common.colourise("&b[Client]: Sent message" + (String) obj));
                ObjectOutputStream out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
                out.writeObject(obj);
                out.close();
                in.close();
                dataStream.close();
            }
                catch (IOException | ClassNotFoundException ex) {
                if (ex instanceof ClassNotFoundException) {
                    System.out.println("Client Mismatch!");
                }
                ex.printStackTrace();
                }
            }
    }


    public void setPort(int port) {
        this.port = port;
    }

    private void checkInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (input.equalsIgnoreCase("help")) {
            System.out.println(Common.colourise("&aThis help menu has not yet been completed."));
            checkInput();
        } else {
            System.out.println("Unknown command...");
        }
    }
}

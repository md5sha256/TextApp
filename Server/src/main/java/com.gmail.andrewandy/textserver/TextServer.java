package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.logging.Level;

public class TextServer extends Thread {

    private ServerSocket serverSocket;
    private int port = -1;
    private static TextServer instance;
    private boolean run = true;

    private Set<UUID> connected = new HashSet<>();

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
        while (run)
            try {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Object obj = null;
                try {
                    obj = objectInputStream.readObject();
                    if (obj instanceof UUID && !connected.contains(obj)) {
                        connected.add((UUID) obj);
                    }
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                if (obj == null) {
                    run = false;
                }
                if (obj instanceof String) {
                    String message = (String) obj;
                    System.out.println(Common.colourise("&aRemote Message: &f" + message));
                }

            } catch (SocketTimeoutException so) {
                System.out.println("Connection timed out!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("help")) {
                System.out.println(Common.colourise("&aThis help menu has not yet been completed."));
            }
    }

    public static TextServer getInstance() {
        if (instance == null) {
            instance = new TextServer();
        }
        return instance;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

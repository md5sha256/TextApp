package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;
import com.gmail.andrewandy.textserver.util.FirstMessage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private Set<UUID> connected = new HashSet<>();
    private static boolean run = true;


    private TextServer() {

    }

    public static TextServer getInstance() {
        if (instance == null) {
            return new TextServer();
        }
        return instance;
    }

    public final void setupServer(int port) throws IOException {
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
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Object obj = objectInputStream.readObject();
                if (!obj.getClass().isAssignableFrom(String.class)) {
                    System.out.println("Custom object.");
                   break;
                }
                System.out.println(Common.colourise("&b[Client]: Sent message" + (String) obj));

            } catch (SocketTimeoutException so) {
                System.out.println("Connection timed out!");
                System.out.println("Client disconnected.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                Common.log(Level.SEVERE, "Client mis-match...");
                ex.printStackTrace();
            }
            System.out.println("Good bye");
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
        }
        else {
            System.out.println("Unknown command...");
        }
    }
}

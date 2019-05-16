package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;
import com.gmail.andrewandy.textserver.util.FirstMessage;

import javax.xml.soap.Text;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.*;
import java.util.logging.Level;

public class TextServer extends Thread {

    private ServerSocket serverSocket;
    private int port = -1;
    private static TextServer instance;

    private Set<UUID> connected = new HashSet<>();


    private TextServer() {

    }

    public void setupServer(int port) throws IOException {
        if (port != -1) {
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
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Object obj = null;
                try {
                    obj = objectInputStream.readObject();
                    if (obj instanceof FirstMessage) {
                        System.out.println("Client ID " + ((FirstMessage) obj).getClientID() + " Has connected.");
                        connected.add((((FirstMessage) obj).getClientID()));
                    }
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                if (obj == null) {
                    continue;
                }
                if (obj instanceof String) {

                }



            } catch (SocketTimeoutException so) {
                System.out.println("Connection timed out!");
                break;
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("help")) {
                System.out.println(Common.colourise("&aThis help menu has not yet been completed."));
            }
        }
    }

    public static TextServer getInstance() {
        if (instance == null) {
            return new TextServer();
        }
        return instance;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

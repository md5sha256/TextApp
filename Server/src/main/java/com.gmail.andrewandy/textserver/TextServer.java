package com.gmail.andrewandy.textserver;

import com.gmail.andrewandy.textserver.util.Common;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class TextServer extends Thread {

    private ServerSocket socket;
    private Set<UUID> connectedClients = new HashSet<>();
    private Socket accepted;

    public TextServer(int port) throws IOException {
        socket = new ServerSocket(port);
        accepted = socket.accept();
        accepted.setSoTimeout(10000); //10 Seconds.
    }

    @Override
    public void run() {
        if (socket == null || socket.isClosed()) {
            throw new IllegalStateException("Invalid Socket");
        }
        System.out.println("&bStarted server on" + socket.getInetAddress());
        try {
            DataInputStream inputStream = new DataInputStream(socket.accept().getInputStream());
            ObjectInputStream in = new ObjectInputStream(inputStream);
            Object obj = in.readObject();
            if (obj.getClass().isAssignableFrom(UUID.class)) {
                connectedClients.add((UUID) obj);
            }
            else if (!obj.getClass().isAssignableFrom(String.class)) {
                System.out.println(Common.colourise("&eCustom object received."));
            }
            System.out.println(Common.colourise("&b[Client]: " + (String) obj));
        } catch (IOException | ClassNotFoundException ex) {
            if (ex instanceof ClassNotFoundException) {
                Common.log(Level.SEVERE, "&cClient and Server version MisMatch!");
            }
            ex.printStackTrace();
        }
        finally {
            try {
                if (accepted != null) {
                    accepted.setKeepAlive(true);
                }
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendMessage(String... messages) {
        try {
            Socket accepted = socket.accept();
            DataOutputStream outputStream = new DataOutputStream(accepted.getOutputStream());
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(messages);
            out.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Disconnect and close the socket.
     */
    private void disconnect() throws IOException{
        socket.close();
    }
}

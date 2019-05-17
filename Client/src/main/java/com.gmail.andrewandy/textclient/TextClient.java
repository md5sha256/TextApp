package com.gmail.andrewandy.textclient;


import com.gmail.andrewandy.textclient.util.Common;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;

public class TextClient {
  
    private Socket socket;
    private boolean toCheckForMsg = true;

    public TextClient(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    public void sendMessage(String... message) throws IOException {

        if (socket.isClosed() || !socket.isConnected()) {
            Common.log(Level.WARNING, "&bSocket closed or unable to connect to server. Unable to send the message.");
            return;
        }
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        out.close();
        outputStream.close();

    }

    public void setToCheckForMsg(boolean toCheck) {
        this.toCheckForMsg = toCheck;
    }

    public boolean getMessageCheckStatus() {
        return toCheckForMsg;
    }

    public Object checkForMessage(long time) {
        long start = System.currentTimeMillis();
        while ((time == -1) ? toCheckForMsg : System.currentTimeMillis() < start + time) {
            try {
                while (toCheckForMsg) {

                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    ObjectInputStream in = new ObjectInputStream(inputStream);
                    return in.readObject();
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                if (ex instanceof ClassNotFoundException) {
                    System.out.println("&cThis client may be a version behind or ahead of the server.");
                }
            } finally {
                try {
                    if (socket != null && !socket.isClosed())
                        socket.close();
                    System.out.println("&aDisconnected from server.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Failed to disconnect.");
                }

            }
        }
        return null;
    }
}

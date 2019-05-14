package com.gmail.andrewandy.textserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TextServer extends Thread {

    private ServerSocket serverSocket;

    public TextServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                String message = in.readUTF();


                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
                if (message.equalsIgnoreCase("exit")) {
                    server.close();
                }
                else if (message.equalsIgnoreCase("shutdown")) {
                    return;
                }

            } catch (SocketTimeoutException so) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fii.pa.serverapplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author mike
 */
public class GameServer {

    public static final int PORT = 8100;
    private Boolean running = false;
    ServerSocket serverSocket = null;

    public GameServer() throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(2000);
            running = true;
            System.out.println("Server is running");
            while (running) {
                try {
                    System.out.println("Waiting for a client...");
                    Socket socket = serverSocket.accept();
                    System.out.println("A client has connected!");
                    new ClientThread(socket, this).start();
                } catch (SocketTimeoutException e) {
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            serverSocket.close();
            System.out.println("Server has stopped");
        }
    }

    public void stop() {
        if (running) {
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }
}

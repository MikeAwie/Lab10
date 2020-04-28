/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fii.pa.serverapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author mike
 */
class ClientThread extends Thread {

    private Socket socket = null;
    private GameServer server = null;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String request;
            while ((request = in.readLine()) != null && server.isRunning()) {
                String response = respond(request);
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private String respond(String request) {
        if (request.equals("stop")) {
            server.stop();
            return "Server stopped";
        } else {
            return "Server received the request... " + request;
        }
    }
}

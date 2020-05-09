package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    // Define the port on which the server is listening
    public static final int PORT = 8100;
    private boolean running = true;

    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                ClientThread clientThread = new ClientThread(socket);
                clientThread.run();
                boolean closeServer = clientThread.getThreadResponse();
                if(closeServer) {
                    running = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
            System.err.println("Server has been stopped!");
        }
    }

}

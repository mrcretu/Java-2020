package compulsory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

enum Command {
    stop,
    createGame,
    joinGame,
    submitMove
}

public class ClientThread extends Thread {
    private Socket socket = null;
    private boolean closeServer = false;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.err.println("Starting thread execution ...");
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            // Send the response to the oputput stream: server → client
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String command = handleClientCommand(request);
            out.println(command);
            out.flush();
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
                System.err.println("The socket created for client communication has been closed!");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public boolean getThreadResponse() {
        return closeServer;
    }

    private String handleClientCommand(String command) {
        if (command.compareTo(String.valueOf(Command.stop)) == 0) {
            closeServer = true;
            return "Server stopped";
        }
        return "Server received the request ...";
    }
}

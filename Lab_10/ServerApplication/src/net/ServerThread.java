package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import tool.HashMapManager;
import tool.MessageManager;
import tool.Player;

public class ServerThread implements Runnable {

    Socket socket = null;

    public ServerThread(Socket s) {
        this.socket = s;
        Player player = new Player(this.hashCode(), s);
        HashMapManager.getInstance().addPlayer(this.hashCode(), player);
    }

    @Override
    public void run() {
        boolean connected = true;
        String s;
        MessageManager.getInstance().addMessage("Player" + this.hashCode() + "online");

        while (connected) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                s = br.readLine();

                System.out.println("[RECEIVE FROM " + this.hashCode() + "]" + s);

                new Resolver().resolve(this.hashCode(), socket, s);
            } catch (IOException e) {
                connected = false;
                System.out.println(this.hashCode() + " is off");
                new EndDeal().clientOff(this.hashCode());
            }
        }
    }
}
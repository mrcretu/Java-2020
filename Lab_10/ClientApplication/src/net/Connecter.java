package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import ui.GameFrame;

public class Connecter {

    Socket socket = null; // Socket connection

    public void connect() {
        try {
            // Obtain the server IP and port number from the interface
            String ipStr = GameFrame.getInstance().getLoginPanel().getIpTextField().getText();
            String portStr = GameFrame.getInstance().getLoginPanel().getPortTextField().getText();
            int portValue = Integer.parseInt(portStr);
            // Create Socket connection
            socket = new Socket(ipStr, portValue);
            // Set input and output streams
            IOManager.getInstance().setBr(new InputStreamReader(socket.getInputStream()));
            IOManager.getInstance().setPs(socket.getOutputStream());

            Data.connected = true;
            MessageManager.getInstance().addMessage("Server is connected");
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(GameFrame.getInstance(), "Server not found");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(GameFrame.getInstance(), "Server connection error");
        }
    }

    public void disconnect() {
        if (socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
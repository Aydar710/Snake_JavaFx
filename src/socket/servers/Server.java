package socket.servers;

import app.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private List<ClientHandler> clients;
    private boolean isTwoPlayersConnected = false;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        Runnable waitClients = () -> {
            while (!isTwoPlayersConnected) {
                try {
                    System.out.println("Waiting clients");
                    new ClientHandler(serverSocket.accept()).start();
                    if (clients.size() == 2)
                        isTwoPlayersConnected = true;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println("All clients connected");

        };
        Thread waitClientsThread = new Thread(waitClients);
        waitClientsThread.start();
    }

//    @Override
//    public void run() {
//        ServerSocket serverSocket;
//        try {
//            System.out.println("Waiting client");
//            serverSocket = new ServerSocket(6666);
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//
//        while (true) {
//            try {
//                new ClientHandler(serverSocket.accept()).start();
//            } catch (Exception e) {
//                throw new IllegalStateException(e);
//            }
//        }
//    }


    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;


        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
            System.out.println("New client connected");
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }


}

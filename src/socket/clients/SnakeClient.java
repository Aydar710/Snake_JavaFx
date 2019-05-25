package socket.clients;

import app.Snake;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.*;
import java.net.Socket;

public class SnakeClient extends Socket {

    private Socket clientSocket;
    private PrintWriter out;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private BufferedReader in;
    private Snake snake;
    private ObservableList<Node> playersSnake;
    private ObservableList<Node> enemiesSnake;


    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            new Thread(eventListener).start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    public void sendSnakeBody(ObservableList<Node> snake) {
        out.println(snake);
    }

    private Runnable eventListener = () -> {
        while (true) {
            try {
                ObservableList<Node> list = (ObservableList<Node>) objectInputStream.readObject();
                if (list != null)
                    snake.setSnake(list);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    };
}

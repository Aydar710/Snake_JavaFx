package socket.programs;

import socket.clients.SnakeClient;

public class ClientStart2 {

    public static void main(String[] args) {
        SnakeClient client = new SnakeClient();

        client.startConnection("127.0.0.1", 6666);
    }
}

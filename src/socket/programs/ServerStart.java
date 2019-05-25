package socket.programs;

import socket.servers.Server;

public class ServerStart {
    public static void main(String[] args) {
        Server server = new Server();
        server.start(6666);
    }
}

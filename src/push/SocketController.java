package push;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketController {
    private Thread thread;
    public static HashMap<String, Socket> usersSocket;

    public SocketController() {
        this.thread = new Thread(new SocketController.Worker());
        usersSocket = new HashMap<>();
    }

    public void start() {
        thread.start();
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8082);
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Socket socket = serverSocket.accept();
                        GetSocketController getSocketController = new GetSocketController(socket,usersSocket);
                        getSocketController.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

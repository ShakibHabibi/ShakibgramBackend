package push;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.MessageInfo;
import model.MessageSocket;
import utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class PostSocketController {
    private Thread thread;
    private String userId;
    private Object object;
    private String pre;

    public PostSocketController(String userId, Object object, String pre) {
        this.thread = new Thread(new Worker());
        this.userId = userId;
        this.object = object;
        this.pre = pre;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = SocketController.usersSocket.get(userId);
                if (socket != null && socket.getInetAddress().isReachable(5000)) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    objectOutputStream.writeObject(pre + gson.toJson(object));
                    objectOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

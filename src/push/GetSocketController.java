package push;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.dao.MessageDao;
import db.dao.daoImpl.MessageDaoImpl;
import model.ChatInfo;
import model.MessageInfo;
import model.MessageSocket;
import model.UpdateStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class GetSocketController {
    private Socket socket;
    private Thread thread;
    private HashMap<String, Socket> usersSocket;

    public GetSocketController(Socket socket, HashMap<String, Socket> usersSocket) {
        this.socket = socket;
        this.usersSocket = usersSocket;
        this.thread = new Thread(new Worker());
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
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    InputStream inputStream = socket.getInputStream();
                    if (inputStream.available() > 0) {
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        Object object = objectInputStream.readObject();
                        String command = (String) object;
                        if (command.startsWith("userInfo")) {
                            String[] users = command.split(" ");
                            if (usersSocket.containsKey(users[1])) {
                                usersSocket.remove(users[1]);
                            }
                            usersSocket.put(users[1], socket);
                        } else if (command.startsWith("message")) {
                            MessageDao messageDao = new MessageDaoImpl();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            messageDao.sendMessage(gson.fromJson(command.substring(8), MessageSocket.class));
                        } else if (command.startsWith("status")) {
                            MessageDao messageDao = new MessageDaoImpl();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            messageDao.updateStatus(gson.fromJson(command.substring(7), UpdateStatus.class));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

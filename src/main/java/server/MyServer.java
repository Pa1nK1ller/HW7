package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    public static final int PORT = 8081;

    private List<ClientHandler> clients;
    private AuthService authService = new DBAuthService();
    private ExecutorService executorService;

    public MyServer() {
        executorService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            DataBase.connect();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Ожидаем подключение клентов");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DataBase.disconnect();
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler client : clients) {
            sb.append(client.getNick()).append(" ");
        }
        Message message = new Message();
        message.setMessage(sb.toString());
        broadcastMessage(message);
    }

    public synchronized void privateMessage(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler client : clients) {
            if (client.getNick().equals(nickTo)) {
                Message message = new Message();
                message.setNick(from.getNick());
                message.setMessage(msg);
                client.sendMessage(message);
                return;
            }
        }
        Message message = new Message();
        message.setMessage("Клиент с таким ником не подключен");
        from.sendMessage(message);
    }

    public synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);

        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler client : clients) {
            if (nick.equals(client.getNick()))
                return true;
        }
        return false;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientsList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientsList();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}

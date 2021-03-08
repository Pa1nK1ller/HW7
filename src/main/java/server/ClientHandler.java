package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientHandler {

    private Socket socket;
    private MyServer myServer;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String nick;
    private static final Logger LOG = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(MyServer myServer, Socket socket) {

        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            myServer.getExecutorService().execute(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    {
                        closeConnection();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        Message message = new Message();
        message.setMessage(nick + " вышел из чата");
        myServer.broadcastMessage(message);
        try {
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void authentication() {
        while (true) {
            try {
                AuthMessage message = new Gson().fromJson(dataInputStream.readUTF(), AuthMessage.class);
                String nick = myServer.getAuthService().getNickByLoginAndPass(message.getLogin(), message.getPassword());
                if (nick != null && !myServer.isNickBusy(nick)) {
                    message.setAuthenticated(true);
                    message.setNick(nick);
                    this.nick = nick;
                    dataOutputStream.writeUTF(new Gson().toJson(message));
                    Message broadcastMsg = new Message();
                    broadcastMsg.setMessage(nick + " вошел в чат");
                    myServer.broadcastMessage(broadcastMsg);
                    myServer.subscribe(this);
                    this.nick = nick;
                    LOG.info("User "+message.getLogin()+ " authentication");
                    return;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void readMessage() throws IOException {
        while (true) {
            Message message = new Gson().fromJson(dataInputStream.readUTF(), Message.class);
            message.setNick(nick);
            System.out.println(message);
            if (!message.getMessage().startsWith("/")) {
                myServer.broadcastMessage(message);
                continue;
            }
            String[] tokens = message.getMessage().split("\\s");
            switch (tokens[0]) {
                case "/end" -> {
                    closeConnection();
                    return;
                }
                case "/w" -> {// /w <nick> <message>
                    if (tokens.length < 3) {
                        Message msg = new Message();
                        msg.setMessage("Не хватает параметров, необходимо отправить команду следующего вида: /w <ник> <сообщение>");
                        this.sendMessage(msg);
                    }
                    String nick = tokens[1];
                    String msg = tokens[2];
                    myServer.privateMessage(this, nick, msg);

                }
                case "/changeNick" -> {//changeNick <nick> <newNick>

                    if (tokens.length < 3) {
                        Message msg = new Message();
                        msg.setMessage("Невозможно сменить ник, Введите команду следующего вида /changeNick <ник> <новый ник>");
                        this.sendMessage(msg);
                        break;
                    }
                    if (myServer.getAuthService().changeNickname(this.nick, tokens[2])) {
                        this.nick = tokens[2];
                        Message msg = new Message();
                        msg.setMessage("Ник изменен");
                        this.sendMessage(msg);
                        myServer.broadcastClientsList();
                    } else {
                        Message msg = new Message();
                        msg.setMessage("Ник занят");
                    }


                }
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            dataOutputStream.writeUTF(new Gson().toJson(message));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}

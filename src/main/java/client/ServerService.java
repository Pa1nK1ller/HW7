package client;

import server.Message;

import java.io.IOException;

public interface ServerService {

    boolean isConnected();
    void openConnection() ;
    public void closeConnection();
    String authorization(String login, String password) throws IOException;


    void sendMessage(String message);
    Message readMessages();
}

package server;

public interface AuthService {
    void start();
    void stop();

    String getNickByLoginAndPass(String login, String password);
    boolean changeNickname(String currentNickname, String newNickname);
    }

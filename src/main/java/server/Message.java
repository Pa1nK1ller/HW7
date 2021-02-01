package server;

public class Message {
    private String nick;
    private String message;

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return message;
    }

    public void setNick(String clientId) {
        this.nick = clientId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "nick='" + nick + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

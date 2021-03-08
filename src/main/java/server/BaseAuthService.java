package server;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private List<Entry> entries;

/*    public BaseAuthService() {
        this.entries = new ArrayList<>();
        for (int i = 0; i <=10 ; i++) {
            entries.add(new Entry("login"+i,"pass"+i,"nick"+i));
        }

    }*/

    private class Entry {
        private String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }


    }

    @Override
    public void start() {
        System.out.println("Сервис авторизации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис авторизации остановлен");
    }

    @Override
    public String getNickByLoginAndPass(String login, String password) {

/*        return DataBase.getUserNickname(login,password);
        for (Entry entry : entries) {
            if (login.equals(entry.login) && password.equals(entry.password)) {
                return entry.nick;
            }
        }*/
        return null;
    }

    @Override
    public boolean changeNickname(String currentNickname, String newNickname) {
        return false;
    }
}

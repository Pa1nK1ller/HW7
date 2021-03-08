package server;

public class DBAuthService implements AuthService{
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
        return DataBase.getUserNickname(login,password);
    }
    public boolean changeNickname(String currentNickname, String newNickname){
        return DataBase.changeUserNickname(currentNickname,newNickname);
    }
}

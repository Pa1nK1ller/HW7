package client;



import java.util.Timer;
import java.util.TimerTask;

public class ClientApp {
    public static void main(String[] args) {
        new MyClient().setVisible(true);

/*       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (MyClient.isAuthorization()) {
                    timer.cancel();
                } else {
                    System.exit(0);

                }
            }
        }, 10 * 1000);*/
    }
}

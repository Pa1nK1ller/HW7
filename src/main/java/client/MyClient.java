package client;

import server.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyClient extends JFrame {

    private ServerService serverService;

    public MyClient() {
        super("Чат");
        serverService = new SocketServerService();
        serverService.openConnection();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setBounds(400,400,500,500);

        TextArea mainChat = new TextArea();
        mainChat.setSize(100,300);

        TextArea myMessage = new TextArea();
        myMessage.setSize(100,300);

        Button send = new Button("Send");
        send.setSize(50,200);

        send.addActionListener(actionEvent -> sendMessage(mainChat,myMessage));

        myMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent keyEvent){
                if (keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
                    sendMessage(mainChat,myMessage);
                }
            }
        });

        new Thread(()->{
            while (true){
                printToUi(mainChat, serverService.readMessages());

            }

        }).start();

        add(mainChat);
        add(send);
        add(myMessage);
    }

    private void sendMessage(TextArea mainChat, TextArea myMessage) {

        serverService.sendMessage(myMessage.getText());
        myMessage.setText("");
    }

    private void printToUi(TextArea mainChat, Message message){
        mainChat.append("\n");
        mainChat.append(message.getNick()+ " написал: "+ message.getMessage());
    }
}

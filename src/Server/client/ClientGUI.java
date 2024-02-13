package Server.client;

import Server.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    private final JTextArea log = new JTextArea();
    private final JPanel topPanel = new JPanel(new GridLayout(2,3));
    private final JTextField  tfIp = new JTextField("127.0.0.1");
    private final JTextField  tfPort = new JTextField("8189");
    private final JTextField  tfLogin = new JTextField("");
    private final JPasswordField  tfPassword = new JPasswordField("12345");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel bottomPanel = new JPanel(new GridLayout(1,2));
    private final JTextField tfMessage = new JTextField("");
    private final JButton btnSend = new JButton("Send");

    private int isOnline = 0;
    ServerWindow serverWindow;
    private final JTextArea serversLog = new JTextArea();
    private int serverStatus;

    public ClientGUI(ServerWindow serverWindow){
        this.serverWindow = serverWindow;

        if(serverWindow.readLog() != null){
            log.append(serverWindow.readLog());
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH,HEIGHT);
        setTitle("Chat client");

        topPanel.add(tfIp);
        topPanel.add(tfPort);
        topPanel.add(tfLogin);
        topPanel.add(tfPassword);
        topPanel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        add(topPanel, BorderLayout.NORTH);

        bottomPanel.add(tfMessage);
        bottomPanel.add(btnSend);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        add(bottomPanel, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        setVisible(true);
    }

    private void login(){
        serverStatus = serverWindow.getServerMode();
        if(serverStatus != 1){
            log.append("Сервер не в сети!\n");
        }  else {
            if ((!tfLogin.getText().isEmpty())) {
                if (isOnline == 1) {
                    log.append("Вы уже вошли в систему!\n");
                } else {
                    serverWindow.connectUser(this);
                    log.append("Вы успешно вошли в систему!\n");
                    ServerWindow.appendLog(String.format(tfLogin.getText() + " подключается!\n"));
                    isOnline = 1;
                }
            } else {
                log.append("Неуказано имя пользователя!\n");
            }
        }

    }

    private void sendMessage(){
        serverStatus = serverWindow.getServerMode();
        if(serverStatus != 1){
            log.append("Сервер не в сети!\n");
        } else {
            if(isOnline != 0) {
                ServerWindow.appendLog(String.format(tfLogin.getText() + ": " + tfMessage.getText() + "\n"));
                serverWindow.answerAll(String.format(tfLogin.getText() + ": " + tfMessage.getText()));
            } else {
                log.append("Вы не вошли в систему!\n");
            }
        }
    }

    public void answer(String text){
        log.append(text + "\n");
    }

}

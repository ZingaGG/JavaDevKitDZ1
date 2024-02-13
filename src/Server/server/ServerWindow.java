package Server.server;

import Server.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;
    public static final String LOG_PATH = "src/Server/log.txt";
    private int serverMode = 0;

    private static JTextArea log = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(log);
    JButton btnStart, btnStop;
    ArrayList<ClientGUI> clientGUIList = new ArrayList<>();

    public ServerWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setTitle("Server");
        setResizable(false);
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverMode == 1){
                    checkServerStatus();
                } else {
                    log.append("Сервер включен!\n");
                }
                serverMode = 1;
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverMode == 0){
                    checkServerStatus();
                } else {
                    log.append("Сервер выключен!\n");
                }
                serverMode = 0;
            }
        });

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void checkServerStatus(){
        if(serverMode == 1){
            log.append("Сервер уже включен!\n");
        } else
        {
            log.append("Сервер уже выключен!\n");
        }
    }

    public int getServerMode(){
        return serverMode;
    }

    public static void appendLog(String string){
        log.append(string);
    }

    public void connectUser(ClientGUI clientGUI){
        if (serverMode == 0){
            return;
        }
        clientGUIList.add(clientGUI);
    }

    public void answerAll(String text){
        for (ClientGUI clientGUI: clientGUIList){
            clientGUI.answer(text);
            saveInLog(text);
        }
    }

    private void saveInLog(String text){
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}


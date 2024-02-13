import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;
    private int gameMode;
    private int gameSize = 3;
    private int victoryLength = 3;

    JButton btnStart;

    SettingWindow(GameWindow gameWindow){
        btnStart = new JButton("Start new game");

        setLocationRelativeTo(gameWindow);
        setSize(WIDTH, HEIGHT);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                gameWindow.startNewGame(gameMode, gameSize, gameSize, victoryLength);
            }
        });

        add(createMainPanel());
        add(btnStart, BorderLayout.SOUTH);
    }

    public JPanel createModePanel(){
        JPanel jPanel = new JPanel(new GridLayout(3,1));
        JLabel jLabel = new JLabel("Выберите режимы игры");
        JRadioButton j1 = new JRadioButton("Человек против компьютера");
        JRadioButton j2 = new JRadioButton("Человек против человека");
        ButtonGroup buttonGroup = new ButtonGroup();

        j1.setSelected(true);

        j1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = 0;
            }
        });

        j2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = 1;
            }
        });

        buttonGroup.add(j1);
        buttonGroup.add(j2);
        jPanel.add(jLabel);
        jPanel.add(j1);
        jPanel.add(j2);

        return jPanel;
    }

    public JPanel createFieldPanel(){
        JPanel jPanel = new JPanel(new GridLayout(3,1));
        JLabel jLabel = new JLabel("Выберите размеры поля");
        JSlider jSlider = new JSlider(3,10,3);
        JLabel jLabelSl = new JLabel("Установленный размер поля " + jSlider.getValue());

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jLabelSl.setText("Установленный размер поля " + jSlider.getValue());
                gameSize = jSlider.getValue();
            }
        });

        jPanel.add(jLabel);
        jPanel.add(jLabelSl);
        jPanel.add(jSlider);

        return jPanel;
    }

    public JPanel createVictoryPanel(){
        JPanel jPanel = new JPanel(new GridLayout(3,1));
        JLabel jLabel = new JLabel("Выберите длину для победы");
        JSlider jSlider = new JSlider(3,10,3);
        JLabel jLabelSl = new JLabel("Установленная длинна " + jSlider.getValue());

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jLabelSl.setText("Установленная длина " + jSlider.getValue());

                victoryLength = jSlider.getValue();

                if(victoryLength > gameSize){
                    victoryLength = gameSize;
                    jSlider.setValue(victoryLength);
                }

            }
        });

        jPanel.add(jLabel);
        jPanel.add(jLabelSl);
        jPanel.add(jSlider);

        return jPanel;
    }

    public JPanel createMainPanel(){
        JPanel jPanel = new JPanel(new GridLayout(3,1));
        jPanel.add(createModePanel());
        jPanel.add(createFieldPanel());
        jPanel.add(createVictoryPanel());
        return jPanel;
    }
}

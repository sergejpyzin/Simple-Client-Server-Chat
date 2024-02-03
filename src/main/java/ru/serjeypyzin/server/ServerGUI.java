package ru.serjeypyzin.server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {
    private final static int SERVER_WINDOW_WIDTH = 400;
    private final static int SERVER_WINDOW_HEIGHT = 400;

    public ServerGUI () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SERVER_WINDOW_WIDTH, SERVER_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("SERVER");

        add(getButtonMenu(), BorderLayout.SOUTH);

        setVisible(true);


    }

    private JPanel getButtonMenu() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton buttonStart = new JButton("START");
        JButton buttonStop = new JButton("STOP");

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        return buttonPanel;
    }
}

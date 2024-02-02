package ru.serjeypyzin.server;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame {

    private final static int SERVER_WINDOW_WIDTH = 400;
    private final static int SERVER_WINDOW_HEIGHT = 400;

    SettingServerWindow settingServerWindow = new SettingServerWindow();

    public ServerWindow () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SERVER_WINDOW_WIDTH, SERVER_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("SERVER");

        add(settingServerWindow.getButtonMenu(), BorderLayout.SOUTH);

        setVisible(true);


    }
}

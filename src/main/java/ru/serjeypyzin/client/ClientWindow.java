package ru.serjeypyzin.client;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {

    private final static int CLIENT_WINDOW_WIDTH = 400;
    private final static int CLIENT_WINDOW_HEIGHT = 400;

    SettingClientWindow settingClientWindow = new SettingClientWindow();
    public ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(CLIENT_WINDOW_WIDTH, CLIENT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        setTitle("Client");
        setResizable(false);

        add(settingClientWindow.getTitlePanel(), BorderLayout.NORTH);
        add(settingClientWindow.getSendPanel(), BorderLayout.SOUTH);
        add(settingClientWindow.getMessageArea(), BorderLayout.CENTER);


        setVisible(true);

    }



}

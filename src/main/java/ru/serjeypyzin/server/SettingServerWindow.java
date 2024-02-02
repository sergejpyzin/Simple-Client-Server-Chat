package ru.serjeypyzin.server;

import javax.swing.*;
import java.awt.*;

public class SettingServerWindow {

    JPanel getButtonMenu() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton buttonStart = new JButton("START");
        JButton buttonStop = new JButton("STOP");

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        return buttonPanel;
    }
}

package ru.serjeypyzin.client;

import javax.swing.*;
import java.awt.*;

public class SettingClientWindow extends JFrame {


    JPanel getTitlePanel() {
        JPanel jPanel = new JPanel(new GridLayout(2, 3));

        JTextField ipClient = new JTextField("127.0.0.1");
        JTextField portClient = new JTextField("8080");
        JTextField login = new JTextField("Ivan Igorevich");
        JTextField password = new JTextField();

        JButton logInButton = new JButton("LOGIN");

        jPanel.add(ipClient);
        jPanel.add(portClient);
        jPanel.add(login);
        jPanel.add(password);
        jPanel.add(logInButton);

        return jPanel;
    }

    JTextArea getMessageArea() {

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(messageArea);
        add(jScrollPane);

        return messageArea;
    }


    JPanel getSendPanel() {
        JPanel jPanel = new JPanel(new GridLayout(1, 2));
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        jPanel.add(inputField);
        jPanel.add(sendButton);

        return jPanel;
    }



}

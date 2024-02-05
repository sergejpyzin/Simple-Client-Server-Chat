package ru.serjeypyzin.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JFrame {
    private final static int CLIENT_WINDOW_WIDTH = 400;
    private final static int CLIENT_WINDOW_HEIGHT = 400;
public ClientGUI (){
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(CLIENT_WINDOW_WIDTH, CLIENT_WINDOW_HEIGHT);
    setLocationRelativeTo(null);

    setTitle("Client");
    setResizable(true);

    add(getTitlePanel(), BorderLayout.NORTH);
    add(getSendPanel(), BorderLayout.SOUTH);
    add(getMessageArea(), BorderLayout.CENTER);


    setVisible(true);
}

    JPanel getIpSettingPanel() {
        JPanel jPanel = new JPanel(new GridLayout(1, 3));

        JTextField ipClient = new JTextField("127.0.0.1");
        JTextField portClient = new JTextField("8080");

        jPanel.add(ipClient);
        jPanel.add(portClient);

        return jPanel;
    }
    JPanel getLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(1, 3));

        JTextField clientName = new JTextField("Ivan Ivanovich");
        JPasswordField password = new JPasswordField("123456789");

        JButton buttonLogin = new JButton("LOGIN");

        loginPanel.add(clientName);
        loginPanel.add(password);
        loginPanel.add(buttonLogin);

        return loginPanel;
    }

    JPanel getTitlePanel (){
        JPanel jPanel = new JPanel(new GridLayout(2, 1));

        jPanel.add(getIpSettingPanel());
        jPanel.add(getLoginPanel());

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
        JPanel sendPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(inputField.getText());
                    inputField.setText("");
                }
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String inputMessage = inputField.getText();
            System.out.printf(inputMessage);
        });

        sendPanel.add(inputField);
        sendPanel.add(sendButton, BorderLayout.EAST);

        return sendPanel;
    }

    private void sendMessage(String text) {


    }

}

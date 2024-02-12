package ru.serjeypyzin.client;

import ru.serjeypyzin.server.ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame implements ClientView {
    private static final int CLIENT_WINDOW_WIDTH = 400;
    private static final int CLIENT_WINDOW_HEIGHT = 400;
    private final Client client;

    private final JTextArea messageArea;

    public ClientGUI(ServerGUI serverGUI) {
        setSize(CLIENT_WINDOW_WIDTH, CLIENT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Client");
        setResizable(true);

        client = new Client(serverGUI, this);

        add(getTitlePanel(), BorderLayout.NORTH);
        add(getSendPanel(), BorderLayout.SOUTH);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        add(jScrollPane);

        setVisible(true);
    }

    private JPanel getIpSettingPanel() {
        JPanel jPanel = new JPanel(new GridLayout(1, 3));
        JTextField ipClient = new JTextField("127.0.0.1");
        JTextField portClient = new JTextField("8080");

        jPanel.add(ipClient);
        jPanel.add(portClient);
        return jPanel;
    }

    private JPanel getLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(1, 3));
        JTextField clientName = new JTextField("Ivan Ivanovich");
        JPasswordField password = new JPasswordField("123456789");

        JButton buttonLogin = new JButton("LOGIN");
        loginPanel.add(clientName);
        loginPanel.add(password);
        loginPanel.add(buttonLogin);

        buttonLogin.addActionListener(e -> client.connectToServer(clientName.getText()));
        return loginPanel;
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.add(getIpSettingPanel());
        titlePanel.add(getLoginPanel());
        return titlePanel;
    }

    private JPanel getSendPanel() {
        JPanel sendPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    client.createMessage(inputField.getText());
                    inputField.setText("");
                }
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            client.createMessage(inputField.getText());
            inputField.setText("");
        });

        sendPanel.add(inputField);
        sendPanel.add(sendButton, BorderLayout.EAST);
        return sendPanel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            client.disconnectFromServer();
            dispose();
        }
        super.processWindowEvent(e);
    }

    @Override
    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + System.lineSeparator()));
    }
}

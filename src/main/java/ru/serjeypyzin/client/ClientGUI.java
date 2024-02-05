package ru.serjeypyzin.client;

import ru.serjeypyzin.server.ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame {
    private final static int CLIENT_WINDOW_WIDTH = 400;
    private final static int CLIENT_WINDOW_HEIGHT = 400;
    private boolean isConnected;
    private String userName;
    private JPanel titlePanel;
    private JTextField ipClient, portClient, clientName, inputField;
    private JPasswordField password;
    private JButton buttonLogin;
    private JTextArea messageArea;
    private String message;
    private ServerGUI serverGUI;

    public ClientGUI(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;

        setSize(CLIENT_WINDOW_WIDTH, CLIENT_WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        setTitle("Client");
        setResizable(true);

        add(getTitlePanel(), BorderLayout.NORTH);
        add(getSendPanel(), BorderLayout.SOUTH);
        add(getMessageArea(), BorderLayout.CENTER);


        setVisible(true);
    }

    private JPanel getIpSettingPanel() {
        JPanel jPanel = new JPanel(new GridLayout(1, 3));

        ipClient = new JTextField("127.0.0.1");
        portClient = new JTextField("8080");

        jPanel.add(ipClient);
        jPanel.add(portClient);

        return jPanel;
    }

    private JPanel getLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(1, 3));

        clientName = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123456789");

        buttonLogin = new JButton("LOGIN");

        loginPanel.add(clientName);
        loginPanel.add(password);
        loginPanel.add(buttonLogin);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        return loginPanel;
    }

    private JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));

        titlePanel.add(getIpSettingPanel());
        titlePanel.add(getLoginPanel());

        return titlePanel;
    }


    private JTextArea getMessageArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(messageArea);
        add(jScrollPane);

        return messageArea;
    }


    private JPanel getSendPanel() {
        JPanel sendPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    createMessage();
                    inputField.setText("");
                }
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            createMessage();
            inputField.setText("");
        });

        sendPanel.add(inputField);
        sendPanel.add(sendButton, BorderLayout.EAST);

        return sendPanel;
    }

    private void sendMessage(String message) {
        if (isConnected) {
            serverGUI.sendMessage(userName + ": " + message);
        } else {
            addedInfoToLog("Нет подключения к серверу");
        }
    }



    private void connectToServer() {
        if (serverGUI.connectClient(this)){
            addedInfoToLog("Вы успешно подключились!\n");
            isConnected = true;
            userName = clientName.getText();
            String logInfo = serverGUI.getInfoFromLog();
            if (logInfo != null){
                addedInfoToLog(logInfo);
            }
        } else {
            addedInfoToLog("Подключение не удалось");
        }
    }

    public void disconnectFromServer() {
        if (isConnected) {
            isConnected = false;
            serverGUI.disconnectClient(this);
            addedInfoToLog("Вы были отключены от сервера!");
        }
    }

    public void createMessage() {
        if (!isConnected) {
            addedInfoToLog("Нет подключения к серверу");
            return;
        }
        String text = inputField.getText();
        if (!text.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            serverGUI.sendMessage("[" + dateFormat.format(new Date()) + "] " + userName + ": " + text);
            messageArea.setText(inputField.getText());
        } else {
            addedInfoToLog("Сообщение не может быть пустым");
        }
    }

    public void answerClient(String text){
        addedInfoToLog(text);
    }

    public void addedInfoToLog(String text) {
        messageArea.append(text + System.lineSeparator());
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            disconnectFromServer();
            dispose();
        }
        super.processWindowEvent(e);
    }

    public void dispose(){
        super.dispose();
    }


}


package ru.serjeypyzin.server;

import ru.serjeypyzin.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServerGUI extends JFrame {
    private final static int SERVER_WINDOW_WIDTH = 400;
    private final static int SERVER_WINDOW_HEIGHT = 400;

    private final static String RELATIVE_PATH = "src\\main\\java\\ru\\serjeypyzin\\server\\log_file.txt";

    List<ClientGUI> clients = new ArrayList<>();
    private JTextArea infoArea;
    private boolean isRunning;

    public ServerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SERVER_WINDOW_WIDTH, SERVER_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("SERVER");

        add(getButtonMenu(), BorderLayout.SOUTH);
        add(getInfoArea());

        setVisible(true);
    }

    private JPanel getButtonMenu() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton buttonStart = new JButton("START");
        JButton buttonStop = new JButton("STOP");

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        buttonStart.addActionListener(e -> {
            if (isRunning) {
                addedLogInfo("Сервер работает!");
            } else {
                isRunning = true;
                addedLogInfo("Старт работы сервера!");
            }

        });

        buttonStop.addActionListener(e -> {
            if (!isRunning) {
                addedLogInfo("Сервер остановлен!");
            } else {
                isRunning = false;
                addedLogInfo("Остановка сервера");
            }

        });

        return buttonPanel;
    }

    private JTextArea getInfoArea() {
        infoArea = new JTextArea();
        infoArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(infoArea);
        add(jScrollPane);

        return infoArea;
    }

    public boolean connectClient(ClientGUI client) {
        if (!isRunning) {
            return false;
        }
        clients.add(client);
        return true;
    }

    public void disconnectClient(ClientGUI client) {
        clients.remove(client);
        if (client != null) {
            client.disconnectFromServer();
        }
    }

    public void sendMessage(String message) {
        if (!isRunning) {
            return;
        }
        message += "";
        addedLogInfo(message);
        answerAllClient(message);
        saveInfoToLog(message);
    }

    public void answerAllClient(String message) {
        clients.forEach(client -> client.answerClient(message));
    }

    public void addedLogInfo(String info) {
        infoArea.append(info + System.lineSeparator());
    }

    private void saveInfoToLog(String text) {
        try {
            Path logFilePath = Paths.get(RELATIVE_PATH);

            if (!Files.exists(logFilePath)) {
                Files.createFile(logFilePath);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.APPEND)) {
                writer.write(text + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог: " + e.getMessage());
        }
    }


    private String readInfoFromLog() {
        try {
            return Files.lines(Paths.get(RELATIVE_PATH))
                    .collect(Collectors.joining(System.getProperty("line.separator")));
        } catch (IOException e) {
            System.err.println("Ошибка при чтении лога: " + e.getMessage());
            return null;
        }
    }


    public String getInfoFromLog() {
        return readInfoFromLog();
    }


}


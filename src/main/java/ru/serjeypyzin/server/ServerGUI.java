package ru.serjeypyzin.server;

import ru.serjeypyzin.client.Client;
import ru.serjeypyzin.client.ClientView;
import ru.serjeypyzin.repository.MessageRepository;
import ru.serjeypyzin.repository.MessageRepositoryImpl;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ServerGUI extends JFrame implements ServerView {
    private final static int SERVER_WINDOW_WIDTH = 400;
    private final static int SERVER_WINDOW_HEIGHT = 400;

    private final static String RELATIVE_PATH = "src/main/java/ru/serjeypyzin/server/log_file.txt";

    private final List<ClientView> clients = new ArrayList<>();
    private JTextArea infoArea;
    private boolean isRunning;
    private final MessageRepository messageRepository;

    public ServerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SERVER_WINDOW_WIDTH, SERVER_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("SERVER");

        messageRepository = new MessageRepositoryImpl();

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

    @Override
    public boolean connectClient(Client client) {
        if (!isRunning) {
            return false;
        }
        clients.add(client);
        return true;
    }

    @Override
    public void disconnectClient(Client client) {
        clients.remove(client);
        if (client != null) {
            client.appendMessage("Вы были отключены от сервера!");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (!isRunning) {
            return;
        }
        message += "";
        addedLogInfo(message);
        answerAllClient(message);
        saveInfoToLog(message);
        messageRepository.saveMessage(message);
    }

    private void answerAllClient(String message) {
        clients.forEach(client -> client.appendMessage(message));
    }

    private void addedLogInfo(String info) {
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

    public String getInfoFromLog() {
        return messageRepository.readInfoFromLog();
    }
}

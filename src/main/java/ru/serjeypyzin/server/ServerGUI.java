package ru.serjeypyzin.server;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI extends JFrame {
    private final static int SERVER_WINDOW_WIDTH = 400;
    private final static int SERVER_WINDOW_HEIGHT = 400;

    private JTextArea infoArea;

    public ServerGUI () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SERVER_WINDOW_WIDTH, SERVER_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("SERVER");

        add(getButtonMenu(), BorderLayout.SOUTH);
        infoArea = getInfoArea();
        add(infoArea);

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

    private JTextArea getInfoArea () {
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(infoArea);
        add(jScrollPane);

        return infoArea;
    }

    private void startServer() {
        // Здесь вы можете запустить серверный код
        // Например, создать ServerSocket и начать принимать подключения от клиентов
        // При получении сообщения от клиента, вызывайте метод saveMessageToFile(message)
    }

    private void stopServer() {
        // Здесь вы можете остановить серверный код
    }

    private void saveMessageToFile(String message) {
        // Сохраняем сообщение в файл
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("server_log.txt", true)))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.println(timestamp + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Отображаем сообщение в JTextArea
        infoArea.append(message + "\n");
    }
}

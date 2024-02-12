package ru.serjeypyzin.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MessageRepositoryImpl implements MessageRepository {
    private static final String RELATIVE_PATH = "src/main/java/ru/serjeypyzin/server/messages.txt";

    @Override
    public void saveMessage(String message) {
        try {
            Path messageFilePath = Paths.get(RELATIVE_PATH);

            if (!Files.exists(messageFilePath)) {
                Files.createFile(messageFilePath);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(messageFilePath, StandardOpenOption.APPEND)) {
                writer.write(message + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи сообщения: " + e.getMessage());
        }
    }

    public String readInfoFromLog() {
        try {
            return Files.lines(Paths.get(RELATIVE_PATH))
                    .reduce((a, b) -> a + System.getProperty("line.separator") + b)
                    .orElse("");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении лога: " + e.getMessage());
            return "";
        }
    }
}

package ru.serjeypyzin.client;

import ru.serjeypyzin.server.ServerGUI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Client implements ClientPresent {
    private final ServerGUI serverGUI;
    private final ClientView clientView;
    private boolean isConnected;
    private String userName;

    public Client(ServerGUI serverGUI, ClientView clientView) {
        this.serverGUI = serverGUI;
        this.clientView = clientView;
    }

    @Override
    public void connectToServer(String userName) {
        if (serverGUI.connectClient(this)) {
            clientView.appendMessage("Вы успешно подключились!\n");
            isConnected = true;
            this.userName = userName;
            String logInfo = serverGUI.getInfoFromLog();
            if (logInfo != null) {
                clientView.appendMessage(logInfo);
            }
        } else {
            clientView.appendMessage("Подключение не удалось");
        }
    }

    @Override
    public void disconnectFromServer() {
        if (isConnected) {
            isConnected = false;
            serverGUI.disconnectClient(this);
            clientView.appendMessage("Вы были отключены от сервера!");
        }
    }

    @Override
    public void createMessage(String message) {
        if (!isConnected) {
            clientView.appendMessage("Нет подключения к серверу");
            return;
        }
        if (!message.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            serverGUI.sendMessage("[" + dateFormat.format(new Date()) + "] " + userName + ": " + message);
            clientView.appendMessage("[" + dateFormat.format(new Date()) + "] " + userName + ": " + message);
        } else {
            clientView.appendMessage("Сообщение не может быть пустым");
        }
    }
}

package ru.serjeypyzin.server;

import ru.serjeypyzin.client.Client;

public interface ServerView {
    boolean connectClient(Client client);
    void disconnectClient(Client client);
    void sendMessage(String message);
}

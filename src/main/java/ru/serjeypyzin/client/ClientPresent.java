package ru.serjeypyzin.client;

public interface ClientPresent {
    void connectToServer(String userName);
    void disconnectFromServer();
    void createMessage(String message);
}

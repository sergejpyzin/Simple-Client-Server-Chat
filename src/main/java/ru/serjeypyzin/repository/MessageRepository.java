package ru.serjeypyzin.repository;

public interface MessageRepository {
        void saveMessage(String message);

     String readInfoFromLog();

}

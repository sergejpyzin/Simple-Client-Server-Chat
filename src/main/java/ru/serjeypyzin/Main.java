package ru.serjeypyzin;

import ru.serjeypyzin.client.ClientGUI;
import ru.serjeypyzin.server.ServerGUI;

public class Main {
    public static void main(String[] args) {
        ServerGUI server = new ServerGUI();
        new ClientGUI(server);
        new ClientGUI(server);



    }

}

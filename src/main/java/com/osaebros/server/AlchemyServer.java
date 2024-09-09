package com.osaebros.server;

import com.osaebros.modules.ApplicationFXModuleController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class AlchemyServer implements Runnable {

    private static final Logger log = Logger.getLogger(AlchemyServer.class.getName());

    Socket socket;
    ServerSocket serverSocket;

    final Collection<ConnectedClientHandler> clients;

    final ApplicationFXModuleController applicationFXModuleController;
    final ExecutorService executorService;
    final Integer PORT;

    public AlchemyServer(String address, Integer port, Object view, ApplicationFXModuleController controller) {
        PORT = port;
        this.applicationFXModuleController = controller;
        clients = Collections.synchronizedCollection(new ArrayList<>());
        executorService = WebServerThreadPool.getThreadPool();

    }

    @Override
    public void run() {
        try {
            log.info(() -> "Initiating server on port: %s".formatted(PORT));
            serverSocket = new ServerSocket(PORT);

//            while (true) {
                socket = serverSocket.accept();
                ConnectedClientHandler clientHandler = new ConnectedClientHandler(applicationFXModuleController, socket);
                executorService.submit(() -> new Thread(clientHandler).start());
//            }

        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }
}

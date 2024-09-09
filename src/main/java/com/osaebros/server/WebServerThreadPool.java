package com.osaebros.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServerThreadPool {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return executorService;
    }
}

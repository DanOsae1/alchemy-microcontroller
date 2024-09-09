package com.osaebros.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osaebros.modules.ApplicationFXModuleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ConnectedClientHandler implements Runnable {
    private static final Logger log = Logger.getLogger(AlchemyServer.class.getName());

    Socket socket;

    ObjectMapper objectMapper = new ObjectMapper();

    ApplicationFXModuleController controller;

    BufferedReader bufferedReader;
    PrintWriter out;

    public ConnectedClientHandler(ApplicationFXModuleController controller, Socket socket) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {
        log.info(() -> "Connected to %s".formatted(socket.getInetAddress()));
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String incomingRequest;
            StringBuilder stringBuilder = new StringBuilder();
            int contentLength = 0;

            while ((incomingRequest = bufferedReader.readLine()) != null) {
                stringBuilder.append(incomingRequest).append("\r\n");
                if (incomingRequest.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(incomingRequest.substring("Content-Length:".length()).trim());
                }
            }

            // Read body
            if (contentLength > 0) {
                char[] body = new char[contentLength];
                bufferedReader.read(body, 0, contentLength);
                log.info(body.toString());
                stringBuilder.append(new String(body));
            }

            String request = stringBuilder.toString();
            if (request.startsWith("POST")) {
                Map<String, String> parsedRequest = parseRequest(request);
                AlchemyRequest alchemyRequest = objectMapper.readValue(parsedRequest.get("body"), AlchemyRequest.class);
                log.info(() -> "Request received: %s".formatted(
                        request.toString()
                ));
                //Alert UI of incoming request
                log.info("Commencing pour");
                Thread.sleep(1000);
                controller.dispense(alchemyRequest);
                //Update UI of completion
                Thread.sleep(1000);
                handlePostRequest(parsedRequest, out);
            } else {
                sendResponse(out, "HTTP/1.1 405 Method Not Allowed", "Only POST requests are supported");
            }

        } catch (IOException | InterruptedException e) {
            log.severe(e.getMessage());
        }
    }

    private Map<String, String> parseRequest(String request) {
        Map<String, String> parsedRequest = new HashMap<>();
        String[] lines = request.split("\r\n");

        // Parse the request line
        String[] requestLine = lines[0].split(" ");
        parsedRequest.put("method", requestLine[0]);
        parsedRequest.put("path", requestLine[1]);

        // Parse headers
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                // Body starts after an empty line
                if (i + 1 < lines.length) {
                    parsedRequest.put("body", lines[i + 1]);
                }
                break;
            }
            String[] header = lines[i].split(": ");
            if (header.length == 2) {
                parsedRequest.put(header[0], header[1]);
            }
        }

        return parsedRequest;
    }

    private void handlePostRequest(Map<String, String> parsedRequest, PrintWriter out) {
        // Here you can process the POST request
        // For now, we'll just echo back the received data
        String responseBody = "Received POST request:\n" + parsedRequest.toString();
        sendResponse(out, "HTTP/1.1 201 OK", responseBody);
    }

    private void sendResponse(PrintWriter out, String status, String body) {
        out.println(status);
        out.println("Content-Type: text/plain");
        out.println("Content-Length: " + body.length());
        out.println();
        out.println(body);
    }

}

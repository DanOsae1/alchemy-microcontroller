package com.osaebros.view;

import com.osaebros.model.State;
import com.osaebros.modules.ApplicationFXModuleController;
import com.osaebros.util.app.ViewMixin;
import com.osaebros.view.functions.JSFunctionMapper;
import javafx.concurrent.Worker;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class AlchemyWebUI extends BorderPane implements ViewMixin<State, ApplicationFXModuleController> {

    private WebView webView;
    private WebEngine webEngine;
    private CompletableFuture<Void> pageLoadFuture;

    public AlchemyWebUI(ApplicationFXModuleController controller) {
        init(controller);
    }

    @Override
    public void initializeSelf() {
        ViewMixin.super.initializeSelf();
        //load all fonts, stylesheets and add to ui
        webView = new WebView();
        webEngine = webView.getEngine();
        pageLoadFuture = new CompletableFuture<>();
    }

    @Override
    public void initializeParts() {

    }

    @Override
    public void layoutParts() {
        setCenter(webView);
    }

    private void callJavaScript(String function, Object... args) {
        pageLoadFuture.thenRun(() -> {
            StringBuilder sb = new StringBuilder(function).append("(");
            for (int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(",");
                if (args[i] instanceof String) {
                    sb.append("'").append(args[i]).append("'");
                } else {
                    sb.append(args[i]);
                }
            }
            sb.append(")");
            webEngine.executeScript(sb.toString());
        });
    }

    @Override
    public void setupUiToActionBindings(ApplicationFXModuleController controller) {
        URL url = getClass().getResource("/index.html");
        webEngine.load(url.toExternalForm());

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                setupJSToJavaFunctions(controller, webEngine);
            }
        });
    }

    private void setupJSToJavaFunctions(ApplicationFXModuleController controller, WebEngine webEngine) {
        String bridgeScript =
                "window.javaFunctionCall = function(functionName, ...args) {" +
                        "   var argString = JSON.stringify(args);" +
                        "   return window.java.call(functionName, argString);" +
                        "};" +
                        "window.java = {" +
                        "   call: function(functionName, argsJson) {" +
                        "       var result = jsFunctionMapper.call(functionName, argsJson);" +
                        "       return result ? JSON.parse(result) : null;" +
                        "   }" +
                        "};" +
                        "console.log = function(message) { jsFunctionMapper.log(message); };";

        webEngine.executeScript(bridgeScript);
        JSFunctionMapper jsFunctionMapper = new JSFunctionMapper(controller);
        webEngine.executeScript("var jsFunctionMapper = {};");
        webEngine.executeScript("jsFunctionMapper.call = function(fn, args) { return " + jsFunctionMapper.getClass().getName() + ".call(fn, args); };");
        webEngine.executeScript("jsFunctionMapper.log = function(msg) { " + jsFunctionMapper.getClass().getName() + ".log(msg); };");

    }

    @Override
    public void setupModelToUiBindings(State model) {
        callJavaScript("updateUser", "Updated" + LocalDateTime.now());
    }
}



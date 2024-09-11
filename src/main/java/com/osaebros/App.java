package com.osaebros;

import com.osaebros.model.State;
import com.osaebros.modules.ApplicationFXModuleController;
import com.osaebros.server.AlchemyServer;
import com.osaebros.util.microprocessor.Pi4jContext;
import com.osaebros.view.AlchemyGraphicalUI;
import com.osaebros.view.AlchemyPhysicalUI;
import com.osaebros.view.AlchemyPhysicalUIEmulator;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App extends Application {

    private ApplicationFXModuleController applicationModule;
    private AlchemyGraphicalUI gui; //To be replaced with web app
    private AlchemyPhysicalUI pui;
    private static final String APPLICATION_TITLE = "Alchemy App";
    private AlchemyServer server;

    private ExecutorService executorService = Executors.newFixedThreadPool(15);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        State initialState = new State();
        applicationModule = new ApplicationFXModuleController(initialState);

        pui = new AlchemyPhysicalUI(applicationModule, Pi4jContext.context());

        Pane gui = new AlchemyGraphicalUI(applicationModule);

        server = new AlchemyServer(3333, applicationModule);
        executorService.execute(server);

        primaryStage.setTitle(APPLICATION_TITLE);

        setupStage(primaryStage, gui);
        primaryStage.show();

        startPUIEmulator(new AlchemyPhysicalUIEmulator(applicationModule));

    }

    private void setupStage(Stage stage, Pane gui) {
        //if started in DRM
        if (System.getProperty("egl.displayid") != null) {
            // make stage full-screen
            Rectangle2D bounds = Screen.getPrimary().getBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.setResizable(false);

            // to get a nice background and the gui centered
            gui.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            gui.setStyle("-fx-border-color: dodgerblue; -fx-border-width: 3");

            StackPane background = new StackPane(gui);
            background.setStyle("-fx-background-color: linear-gradient(from 50% 0% to 50% 100%, dodgerblue 0%, midnightblue 100%)");

            Scene scene = new Scene(background);

            stage.setScene(scene);
        } else {
            Scene scene = new Scene(gui);
            stage.setScene(scene);
        }
    }

    private void startPUIEmulator(Parent puiEmulator) {
        Scene emulatorScene = new Scene(puiEmulator);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("PUI Emulator");
        secondaryStage.setScene(emulatorScene);
        secondaryStage.show();
    }

    @Override
    public void stop() {
        pui.shutdown();
        applicationModule.shutdown();
    }


}

package com.osaebros.view;

import com.osaebros.model.State;
import com.osaebros.modules.ApplicationFXModuleController;
import com.osaebros.util.app.ViewMixin;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class AlchemyGraphicalUI extends BorderPane implements ViewMixin<State, ApplicationFXModuleController> {

    private Button blinkButton;
    private Label counterLabel;
    private Label infoLabel;
    private static final String HEARTBEAT = "\uf21e";

    public AlchemyGraphicalUI(ApplicationFXModuleController controller) {
        init(controller);
    }

    @Override
    public void initializeSelf() {
        ViewMixin.super.initializeSelf();
        //load all fonts, stylesheets and add to ui
        loadFonts("/fonts/Lato/Lato-Lig.ttf", "/fonts/fontawesome-webfont.ttf");

        //apply your style
        addStylesheetFiles("/mvc/multicontrollerapp/style.css");

        getStyleClass().add("root-pane");
    }

    @Override
    public void initializeParts() {
        blinkButton = new Button(HEARTBEAT);
        blinkButton.getStyleClass().add("icon-button");
    }

    @Override
    public void layoutParts() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // Create column constraints
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().add(columnConstraints);

        // Create row constraints
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints);

        // Add components to the grid
        gridPane.add(blinkButton, 0, 0);
//        gridPane.add(infoLabel, 0, 1);
//        gridPane.add(counterLabel, 0, 2);

        getChildren().add(gridPane);
    }

    @Override
    public void setupModelToUiBindings(State model) {
//        onChangeOf(model.systemInfo)                       // the value we need to observe, in this case that's an ObservableValue<String>, no need to convert it
//                .update(infoLabel.textProperty());         // keeps textProperty and systemInfo in sync
//
//        onChangeOf(model.counter)                          // the value we need to observe, in this case that's an ObservableValue<Integer>
//                .convertedBy(String::valueOf)              // we have to convert the Integer to a String
//                .update(counterLabel.textProperty());      // keeps textProperty and counter in sync
    }

    @Override
    public void setupUiToActionBindings(ApplicationFXModuleController controller) {
        blinkButton.setOnMousePressed(e -> controller.blink());
    }

}

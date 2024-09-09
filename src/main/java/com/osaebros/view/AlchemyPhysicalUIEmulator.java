package com.osaebros.view;


import com.osaebros.model.State;
import com.osaebros.modules.ApplicationFXModuleController;
import com.osaebros.util.app.ViewMixin;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;


public class AlchemyPhysicalUIEmulator extends VBox implements ViewMixin<State, ApplicationFXModuleController> {
    // for each PUI component, declare a corresponding JavaFX-control
    private Label led;
    private Button decreaseButton;

    public AlchemyPhysicalUIEmulator(ApplicationFXModuleController controller) {
        init(controller);
    }

    @Override
    public void initializeSelf() {
        setPrefWidth(250);
    }

    @Override
    public void initializeParts() {
        led = new Label();
        decreaseButton = new Button("Decrease");
    }

    @Override
    public void layoutParts() {
//        setPadding(new Insets(20));
//        setSpacing(20);
//        setAlignment(Pos.CENTER);
//        getChildren().addAll(led, decreaseButton);
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
        gridPane.add(led, 0, 0);
//        gridPane.add(decreaseButton, 0, 1);

        getChildren().add(gridPane);
    }

    @Override
    public void setupUiToActionBindings(ApplicationFXModuleController controller) {
        //trigger the same actions as the real PUI

//        decreaseButton.setOnAction(event -> controller.decreaseCounter());
    }

    @Override
    public void setupModelToUiBindings(State model) {
        //observe the same values as the real PUI

        onChangeOf(model.blinkingTrigger)
                .convertedBy(glows -> glows ? "on" : "off")
                .update(led.textProperty());
    }
}
package com.osaebros.model;

import com.osaebros.util.app.ObservableValue;

public class State {

    public final ObservableValue<Boolean> blinkingTrigger = new ObservableValue<>(false);
    public final ObservableValue<String> systemInfo = new ObservableValue<>("JavaFX " + System.getProperty("javafx.version") + ", running on Java " + System.getProperty("java.version") + ".");
    public ObservableValue<? extends Integer> counter = new ObservableValue<>(0);
    public ObservableValue<? super Boolean> isActive= new ObservableValue<>(false);
}

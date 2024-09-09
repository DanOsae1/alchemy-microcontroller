package com.osaebros.view;

import com.osaebros.model.State;
import com.osaebros.modules.ApplicationFXModuleController;
import com.osaebros.util.app.PuiBase;
import com.osaebros.util.device.SimpleLed;
import com.osaebros.util.device.impl.PIN;
import com.pi4j.context.Context;

import java.time.Duration;

public class AlchemyPhysicalUI extends PuiBase<State, ApplicationFXModuleController> {

    //Define all hardware interactions in this class
    protected SimpleLed led;

    public AlchemyPhysicalUI(ApplicationFXModuleController controller, Context pi4J) {
        super(controller, pi4J);
    }

    @Override
    public void initializeParts() {
        led = new SimpleLed(pi4J, PIN.D22);
    }

    private void setupHardwareToActionBindings(){

    }
    @Override
    public void setupUiToActionBindings(ApplicationFXModuleController controller) {

//        button.onUp(controller::decreaseCounter);
    }

    @Override
    public void setupModelToUiBindings(State model) {
//        onChangeOf(model.isActive)
//                .execute((oldValue, newValue) -> {
//                    if (newValue) {
//                        led.on();
//                    } else {
//                        led.off();
//                    }
//                });

        onChangeOf(model.blinkingTrigger)
                .execute((oldValue, newValue) -> led.blink(4, Duration.ofSeconds(500)));

    }
}
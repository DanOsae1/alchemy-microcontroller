package com.osaebros.modules;

import com.osaebros.model.State;
import com.osaebros.server.AlchemyRequest;
import com.osaebros.util.app.FXModuleOrchestrator;

import java.util.logging.Logger;

public class ApplicationFXModuleController extends FXModuleOrchestrator<State> {

    /**
     * Set the actions of the modules of JavaFX in this class so
     * that the GUI can execute them
     */

    private final Logger log = Logger.getLogger(ApplicationFXModuleController.class.getName());
    private final LedFXModule ledFXModule;
    private final PumpArrayModule pumpArrayModule;

    public ApplicationFXModuleController(State model) {
        super(model);
        ledFXModule = new LedFXModule(model);
        pumpArrayModule = new PumpArrayModule(model);
    }

    @Override
    public void awaitCompletion() {
        super.awaitCompletion();
        ledFXModule.awaitCompletion();
//        counterController.awaitCompletion();
    }

    @Override
    public void shutdown() {
        super.shutdown();
        ledFXModule.shutdown();
    }

    public void setLedGlows(boolean glows) {
        ledFXModule.setIsActive(glows);
    }

    public void blink() {
        log.info("Button pressed");
        ledFXModule.blink();
    }

    public void dispense(AlchemyRequest request) {

    }
}

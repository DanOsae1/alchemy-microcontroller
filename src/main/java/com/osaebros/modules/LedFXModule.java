package com.osaebros.modules;

import com.osaebros.model.State;
import com.osaebros.util.app.FXModuleOrchestrator;

import java.time.Duration;

class LedFXModule extends FXModuleOrchestrator<State> {
    LedFXModule(State model) {
        super(model);
    }

    void setIsActive(boolean glows) {
        setValue(model.isActive, glows);
    }

    void blink() {
        final Duration pause = Duration.ofMillis(500);
        setIsActive(false);
        for (int i = 0; i < 4; i++) {
            setIsActive(true);
            pauseExecution(pause);
            setIsActive(false);
            pauseExecution(pause);
        }
    }

    /**
     * Example for triggering some built-in action in PUI instead of implement it in Controller.
     * <p>
     * Controller can't call PUI-component methods directly. Use a trigger instead.
     */
    void blinkViaBuiltInAction() {
        toggle(model.blinkingTrigger);
    }

}

package com.osaebros.util.device;

import com.osaebros.util.device.impl.DigitalActuator;
import com.osaebros.util.device.impl.PIN;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SimpleLed extends DigitalActuator {

    /**
     * Creates a new SimpleLed component with a custom BCM pin.
     *
     * @param pi4j    Pi4J context
     * @param address Custom BCM pin address
     */
    public SimpleLed(Context pi4j, PIN address) {
        super(pi4j,
                DigitalOutput.newConfigBuilder(pi4j)
                        .id("BCM" + address)
                        .name("LED #" + address)
                        .address(address.getPin())
                        .build());
        logDebug("Created new SimpleLed component");
    }

    /**
     * Sets the LED to on.
     */
    public void on() {
        logDebug("LED turned ON");
        digitalOutput.on();
    }

    public boolean isOn() {
        return digitalOutput.isOn();
    }

    /**
     * Sets the LED to off
     */
    public void off() {
        logDebug("LED turned OFF");
        digitalOutput.off();
    }

    /**
     * Toggle the LED state depending on its current state.
     *
     * @return Return true or false according to the new state of the relay.
     */
    public boolean toggle() {
        digitalOutput.toggle();
        logDebug("LED toggled, now it is %s", digitalOutput.isOff() ? "OFF" : "ON");

        return digitalOutput.isOff();
    }

    @Override
    public void reset() {
        off();
    }

    public void blink(int times, Duration duration) {
        reset();
        logInfo("Blinking lights %s(s) for %s sec(s)", times, duration.toSeconds());
        for (int i = 0; i < times; i++) {
            digitalOutput.pulse((int) duration.toSeconds(), TimeUnit.SECONDS);
        }
    }
}

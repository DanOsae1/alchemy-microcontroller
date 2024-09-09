open module com.osaerbros {
    // Pi4J Modules
    requires com.pi4j;
    requires com.pi4j.library.pigpio;
    requires com.pi4j.library.linuxfs;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    requires com.pi4j.plugin.mock;
    requires com.pi4j.plugin.linuxfs;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // for logging
    requires java.logging;

    //Server
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.web;

    // Module exports
}
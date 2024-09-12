package com.osaebros.util.microprocessor;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider;
import com.pi4j.plugin.mock.provider.serial.MockSerialProvider;
import com.pi4j.plugin.mock.provider.spi.MockSpiProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import com.pi4j.util.Console;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Logger;

public class Pi4jContext {
    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final List<String> piArchs = List.of("aarch64", "arm");
    private static final String SYS_PROP_VENDOR = "java.vendor";
    private static final String SYS_PROP_OS_NAME = "os.name";
    private static final String SYS_PROP_OS_ARCHITECTURE = "os.arch";
    private static final String PI_HOST = "";
    private static final int PI_PORT = 22;


    private Pi4jContext() {

    }

    public static Context context() {
        boolean isOnPi = runsOnPi();
        Context context = createContext(isOnPi);
        printDeviceInformation(context);
        return context;
    }

    private static void printDeviceInformation(Context context) {
        PrintStream loggerPrintStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                log.info(x);
            }
        };
        Platforms platforms = context.platforms();
        platforms.describe().print(loggerPrintStream);
    }

    private static Context createContext(boolean isOnPi) {
        if (!isOnPi) {
            return mockContext();
        }
        final PiGpio piGpio = PiGpio.newNativeInstance();
        return Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio),
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();
    }

    private static PiGpio remoteContext() {
        log.info(() -> "Connecting to remote instance %s %s".formatted(PI_HOST, PI_PORT));
        return PiGpio.newSocketInstance(PI_HOST, PI_PORT);
    }

    private static Context mockContext() {
        log.info("Building Mock context for non pi environment");
        return Pi4J.newContextBuilder()
                .add(new MockPlatform())
                .add(MockAnalogInputProvider.newInstance(),
                        MockAnalogOutputProvider.newInstance(),
                        MockSpiProvider.newInstance(),
                        MockPwmProvider.newInstance(),
                        MockSerialProvider.newInstance(),
                        MockI2CProvider.newInstance(),
                        MockDigitalInputProvider.newInstance(),
                        MockDigitalOutputProvider.newInstance())
                .build();

    }

    private static boolean runsOnPi() {
        final String vendor = System.getProperty(SYS_PROP_VENDOR);
        final String osName = System.getProperty(SYS_PROP_OS_NAME);
        final String osArch = System.getProperty(SYS_PROP_OS_ARCHITECTURE);

        log.info(() -> "System properties: \n************\nVendor: %s \nOS: %s\nArch: %s\n************\n".
                formatted(vendor, osName, osArch));


        return "Raspbian".equals(vendor) ||
                ("Linux".equals(osName) && piArchs.contains(osArch));

    }


}

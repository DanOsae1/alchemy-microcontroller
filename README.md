# alchemy-microcontroller


When running application on raspberry pi for the first time 
```bash
sudo apt-get upgrade && \
sudo apt-get install autoconf automake libtool make tar libaio-dev libssl-dev libapr1-dev lksctp-tools xserver-org && \
sudo apt install maven default-jdk openjfx libwidevinecdm0 
```

mvn clean package && java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml -jar target/original-alchemy-fatjar.jar

Graphics Device initialization failed for :  es2, sw
Error initializing QuantumRenderer: no suitable pipeline found
java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer.getInstance(QuantumRenderer.java:280)
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.init(QuantumToolkit.java:222)
        at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:261)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:267)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:158)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:569)
        at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1091)
Caused by: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:94)
        at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:124)
        at java.base/java.lang.Thread.run(Thread.java:840)
Exception in thread "main" java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:569)
        at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1091)
Caused by: java.lang.RuntimeException: No toolkit found
        at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:273)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:267)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:158)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
        at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
        ... 5 more''''^^''''''^^''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''^'''''''''''''''''^'''''''''''''''''''''''''''''''''''''''^^^''''''''''
# alchemy-microcontroller


When running application on raspberry pi for the first time 
```bash
sudo apt-get upgrade && \
sudo apt-get update && \
sudo apt-get install autoconf automake libtool make tar libaio-dev libssl-dev libapr1-dev lksctp-tools xserver-org && \
sudo apt install maven default-jdk openjfx libwidevinecdm0 git 
```

```bash 
export DISPLAY=:0 && mvn clean package && java --module-path /usr/share/openjfx/lib \
--add-modules=javafx.controls,javafx.graphics \
--add-opens javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
--add-opens javafx.base/com.sun.javafx=ALL-UNNAMED \
-Djavafx.platform=gtk \
-Dprism.verbose=true \
-jar target/alchemy-fatjar.jar
```

When running on PI in editor for development mode
```shell
sudo java \
      --module-path /usr/share/openjfx/lib \
      --add-modules=javafx.controls,javafx.graphics \
      --add-opens javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
      --add-opens javafx.base/com.sun.javafx=ALL-UNNAMED \
      -Djavafx.platform=gtk \
      -Dprism.verbose=true \
      -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
      -jar target/distribution/alchemy-fatjar.jar
```
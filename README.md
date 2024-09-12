# alchemy-microcontroller


When running application on raspberry pi for the first time 
```bash
sudo apt-get upgrade && \
sudo apt-get install autoconf automake libtool make tar libaio-dev libssl-dev libapr1-dev lksctp-tools xserver-org && \
sudo apt install maven default-jdk openjfx libwidevinecdm0 
```

java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml -jar target/original-alchemy-fatjar.jar
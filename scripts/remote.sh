#!/bin/bash

# Get the current user
CURRENT_USER=$(whoami)

export DISPLAY=:0

xhost +local:root

sudo -E bash << EOF

export XAUTHORITY=/home/$CURRENT_USER/.Xauthority

# Run the Java command
java
     -Dprism.verbose=true \
     -Dprism.trace=true \
     --module-path /usr/share/openjfx/lib \
     --add-modules=javafx.controls,javafx.graphics \
     --add-opens javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
     --add-opens javafx.base/com.sun.javafx=ALL-UNNAMED \
     -Djavafx.platform=gtk \
     -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
     -jar /home/$CURRENT_USER/Documents/alchemy-microcontroller/target/distribution/alchemy-fatjar.jar
EOF

xhost -local:root
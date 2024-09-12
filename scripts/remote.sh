#!/bin/bash

# Get the current user
CURRENT_USER=$(whoami)

# Set the DISPLAY variable to use the local display
export DISPLAY=:0

# Allow X server connections from root
xhost +local:root

# Run the Java command with sudo, preserving the user's environment
sudo -E bash << EOF

# Set the XAUTHORITY to allow the root user to connect to the X server
export XAUTHORITY=/home/$CURRENT_USER/.Xauthority

# Run the Java command
java -verbose:gc \
     -Xlog:gc* \
     -Dprism.verbose=true \
     -Dprism.trace=true \
     --module-path /usr/share/openjfx/lib \
     --add-modules=javafx.controls,javafx.graphics \
     --add-opens javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
     --add-opens javafx.base/com.sun.javafx=ALL-UNNAMED \
     -Djavafx.platform=gtk \
     -jar /home/$CURRENT_USER/Documents/alchemy-microcontroller/target/distribution/alchemy-fatjar.jar
EOF

# Disallow X server connections from root
xhost -local:root
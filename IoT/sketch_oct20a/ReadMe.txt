This sketch was originally made for the Arduino mkr wifi 1010, but it should run on all arduino boards. The compiled assembly code should also run on all Atmel boards.
To compile and upload the code you need to download the Arduino IDE.
Note that the IDE requires the sketch-file to be nested in a folder with identical name as the file.

The program makes use of two libraries that you need to manually download to run the code.
Dowload links:
WiFiNINA - https://www.arduinolibraries.info/libraries/wi-fi-nina
LiquidCrystal - https://www.arduinolibraries.info/libraries/liquid-crystal

Add the dowloaded zip folders to the project, open the sketch -> click on the "Sketch" menu -> include library -> "Add .ZIP library..."

The server IP is hardcoded and can only be changed by editing and reuploading the code.
The WiFi credentials are hardcoded as well, but can be changed in the setup phase of the controller.

For everything to work you need to copy the schematic exaclty.
There is no room for error so you should use a voltmeter to ensure all wires are intact and there is no bleed.
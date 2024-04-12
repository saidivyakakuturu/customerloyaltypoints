# Demo Awarding Points

# Pre-requisites
- [java >= 17] (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [maven >= 3.3] (https://maven.apache.org/download.cgi)

# How to develop locally on my laptop?
----------------------

Reference your home directory as HOME_DIR below since Java is platform independent and does not recognize Unix tilde *~* shortcut.
 

### Setup Your Java IDE
This section will describe how to setup your Java IDE so you can debug and develop the code. There are two options for IDE. Eclipse or IntelliJ

#### IntelliJ IDE
##### Run and Import Code
1. Click on Import Project and select your HOME_DIR/customerLoyaltyPoints folder
2. Click on Import project from external model and choose Maven
3. Click Next->Finish and wait for import to complete.

##### Run the WebAPI Server
1. Click on Search
2. In *Search String* box type *App* and hit the *Search* button
3. In the *Search* tab at the bottom right click *main(String[])*
4. In the pop up-menu select Run as—>Run Configuration—>Java Applications
5. Click *Run* and the web server will start
6. Confirm that is running by hitting http://localhost:8080/actuator/health


#### Eclipse IDE
##### Run and Import Code
1. Click on File->Import->Existing Maven Projects->Next
2. Click on Browse and select your HOME_DIR/customerLoyaltyPoints folder
3. Click on Finish and wait for import to complete.

##### Run the WebAPI Server
1. Click on Search->Java
2. In *Search String* box type *App* and hit the *Search* button
3. In the *Search* tab at the bottom right click *main(String[])*
4. In the pop up-menu select Run as—>Run Configuration—>Java Applications
5. Click the tab “Arguments”
6. Click *Run* and the web server will start
7. Confirm that is running by hitting http://localhost:8080/actuator/health


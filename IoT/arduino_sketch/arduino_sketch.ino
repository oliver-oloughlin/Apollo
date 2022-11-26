#include <WiFiNINA.h>
#include <LiquidCrystal.h>

#define sensorPin AS
#define METHOD "GET"
//#define URL "192.168.10.157"
#define URL "172.20.10.2"

#define PORT 8080
#define CONTENTTYPE "application/json"
#define BODY ""
#define BUTTON_PIN_GREEN 6
#define BUTTON_PIN_RED 7
#define BUTTON_PIN_LEFT 1
#define BUTTON_PIN_RIGHT 0


const int rs = 12, en = 11, d4 = 2, d5 = 3, d6 = 4, d7 = 5;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

byte customChar[] = { B11111, B00000, B00000, B00000, B00000, B00000, B00000, B00000 };

char ssid[] = "ifoanFIRE           ";
//char ssid[] = "Valen           ";

//char pass[] = "12345678        ";
char pass[] = "66116611        ";

char pollid[] = "1234            ";

String ssidStringHelper = "";
String passStringHelper = "";
String pollidStringHelper = "";

String pollName = "";
String pollEndTime = "";
int questionIds[] = { 0, 0, 0, 0, 0 };
int currentQuestionIdsIndex = 0;
bool showHomeScreen = false;

String questionName = "";

int red = 0;
int green = 0;

int updateCounter = 48;
int scrollCounter = 0;
int scrollSpaces = 1;


int status = WL_IDLE_STATUS;
//char server[] = "192.168.10.157";
char server[] = "172.20.10.2";

WiFiClient client;

void setup() {
  Serial.begin(9600);
  pinMode(BUTTON_PIN_RED, INPUT_PULLUP);
  pinMode(BUTTON_PIN_GREEN, INPUT_PULLUP);
  pinMode(BUTTON_PIN_LEFT, INPUT_PULLUP);
  pinMode(BUTTON_PIN_RIGHT, INPUT_PULLUP);
  analogWrite(A3, 100);  // Set the brightness to its maximum value
  // set up the LCD's number of columns and rows:
  lcd.createChar(1, customChar);
  lcd.begin(16, 2);  // Print a message to the LCD.

  Serial.print("beginning");

  String ssidDialogText = "ssid:" + String(ssid);
  int ssidCorrect = redGreenDialog(ssidDialogText, "change", "ok", lcd);
  Serial.print(ssidCorrect);
  Serial.print("Svar");
  Serial.print(ssidCorrect);
  Serial.print("Svar");

  if (ssidCorrect == 0) {
    changeSSID(lcd);
  }

  String passDialogText = "pw:" + String(pass);
  int passCorrect = redGreenDialog(passDialogText, "change", "ok", lcd);
  Serial.print(passCorrect);
  Serial.print("Svar");
  Serial.print(passCorrect);
  Serial.print("Svar");

  if (passCorrect == 0) {
    changePass(lcd);
  }

  String pollidDialogText = "p:" + String(pollid);
  int pollCorrect = redGreenDialog(pollidDialogText, "change", "ok", lcd);
  Serial.print(pollCorrect);
  Serial.print("Svar");
  Serial.print(pollCorrect);
  Serial.print("Svar");
  if (pollCorrect == 1) {

  } else {
    changePollid(lcd);
  }

  updateSSIDString();
  updatePassString();
  updatePollidString();
  while (status != WL_CONNECTED) {
    int ssidLength = ssidStringHelper.length() + 1;
    char cleanedSSID[ssidLength];
    ssidStringHelper.toCharArray(cleanedSSID, ssidLength);

    int passLength = passStringHelper.length() + 1;
    char cleanedPass[passLength];
    passStringHelper.toCharArray(cleanedPass, passLength);

    Serial.print("Connecting to: ");
    Serial.println("e" + String(cleanedSSID) + "e");
    Serial.println("e" + String(cleanedPass) + "e");

    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("connectiong to: ");
    lcd.setCursor(0, 1);
    lcd.print(cleanedSSID);
    status = WiFi.begin(cleanedSSID, cleanedPass);
    lcd.print(".");
    delay(250);
    lcd.print(".");
    delay(250);
    lcd.print(".");
    delay(250);
    lcd.print(".");
  }
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
  IPAddress ip = WiFi.localIP();
  IPAddress gateway = WiFi.gatewayIP();
  Serial.println(ip);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Device ip:");
  lcd.setCursor(0, 1);
  lcd.print(ip);
  delay(3000);

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Server ip:");
  lcd.setCursor(0, 1);
  lcd.print(server);
  delay(2000);
  bool ping = pingServer(client);
  printConnectionStatus(lcd, ping);
  updatePoll(client);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(pollName);
  lcd.setCursor(0, 1);
  lcd.print(pollEndTime);
  delay(3000);
  updateQuestion(client);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(questionName);
  updateVote(client);
  lcd.setCursor(0, 1);
  lcd.print("green:" + String(green));
  lcd.print(" red:" + String(red));
  Serial.print("red:");
  Serial.print(red);
  Serial.print(", green:");
  Serial.println(green);
}

void loop() {
  if (updateCounter % 100 == 0) {
    updateVote(client);
    delay(50);
  }

  if (digitalRead(BUTTON_PIN_GREEN) == 0) {
    incrementGreen(client);
    delay(100);
  }

  if (digitalRead(BUTTON_PIN_RED) == 0) {
    incrementRed(client);
    delay(100);
  }

  if (digitalRead(BUTTON_PIN_LEFT) == 0) {
    if (showHomeScreen) {
    } else {
      if (currentQuestionIdsIndex == 0) {
        showHomeScreen = true;
      } else {
        currentQuestionIdsIndex--;
        updateQuestion(client);
        updateVote(client);

      }
    }

    while (digitalRead(BUTTON_PIN_LEFT) == 0) {
      delay(100);
    }
    delay(100);
  }

  if (digitalRead(BUTTON_PIN_RIGHT) == 0) {
    if (showHomeScreen) {
      showHomeScreen = false;
    } else {
      int nextIndex = currentQuestionIdsIndex + 1;
      int nextqustionId = questionIds[nextIndex];
      if (nextqustionId == 0) {
      } else {
        currentQuestionIdsIndex++;
        updateQuestion(client);
        updateVote(client);
      }
    }

    while (digitalRead(BUTTON_PIN_RIGHT) == 0) {
      delay(100);
    }
    delay(100);
  }


  delay(100);
  updateCounter += 1;
  if (showHomeScreen) {
    lcd.clear();
    scroll(lcd, pollName);
    lcd.setCursor(0, 1);
    lcd.print(pollEndTime);
  } else {
    lcd.clear();
    scroll(lcd, questionName);
    lcd.setCursor(0, 1);
    lcd.print("g:" + String(green));
    lcd.print(" r:" + String(red));
  }
}

void updatePoll(WiFiClient client) {
  if (client.connect(server, 8080)) {
    String request = String(METHOD) + " " + "/poll/" + String(pollidStringHelper) + " HTTP/1.1\r\n" + "Host: " + URL + "\r\n" + "Content-Type: " + CONTENTTYPE + "\r\n" + "Connection: close\r\n\r\n" + BODY + "\r\n\r\n";

    //// Making the request.
    client.print(request);

    //// Receiving response headers.
    while (client.connected()) {
      String line = client.readStringUntil('\n');
      // Serial.println("-----HEADERS START-----");
      //Serial.println(line);
      if (line == "\r") {
        // Serial.println("-----HEADERS END-----");
        break;
      }
    }
    // Receiving response body.
    while (client.available()) {
      String line = client.readStringUntil('\n');
      Serial.println(line);
      String titleString = line.substring(line.indexOf("title\":") + 8, line.indexOf("\","));
      String _questionIds = line.substring(line.indexOf("questionIds\":") + 13, line.length() - 1);
      String greenString = _questionIds.substring(_questionIds.indexOf("[") + 1, _questionIds.indexOf("]"));
      String _time = line.substring(line.indexOf("timeToStop\":") + 13, line.indexOf(",\"privatePoll") - 4);
      Serial.println("time: " + _time);

      for (int i = 0; i <= 4; i++) {
        if (greenString.indexOf(",") == -1) {
          questionIds[i] = greenString.toInt();
          greenString = "";
        } else {
          int qid = greenString.substring(0, greenString.indexOf(",")).toInt();
          greenString = greenString.substring(greenString.indexOf(",") + 1, greenString.length());
          questionIds[i] = qid;
        }
      }
      for (int i = 0; i <= 4; i++) {
        Serial.println("all:" + String(questionIds[i]));
      }
      pollName = titleString;
      pollEndTime = _time;
    }
  } else {
    Serial.print("Failed to get poll...");
  }
  if (client.connected()) {
    client.stop();
  }
}

void updateQuestion(WiFiClient client) {
  if (client.connect(server, 8080)) {
    String request = String(METHOD) + " " + "/question/" + String(questionIds[currentQuestionIdsIndex]) + " HTTP/1.1\r\n" + "Host: " + URL + "\r\n" + "Content-Type: " + CONTENTTYPE + "\r\n" + "Connection: close\r\n\r\n" + BODY + "\r\n\r\n";
    //// Making the request.
    client.print(request);
    //// Receiving response headers.
    while (client.connected()) {
      String line = client.readStringUntil('\n');
      // Serial.println("-----HEADERS START-----");
      //Serial.println(line);
      if (line == "\r") {
        // Serial.println("-----HEADERS END-----");
        break;
      }
    }
    // Receiving response body.
    while (client.available()) {
      String line = client.readStringUntil('\n');
      Serial.println(line);
      String titleString = line.substring(line.indexOf("text\":") + 7, line.indexOf("\","));
      Serial.println(titleString);
      questionName = titleString;
    }
  } else {
    Serial.print("Failed to fetch question..");
  }
  if (client.connected()) {
    client.stop();
  }
}

void updateVote(WiFiClient client) {
  if (client.connect(server, 8080)) {
    String request = String(METHOD) + " " + "/question-score/" + String(questionIds[currentQuestionIdsIndex]) + " HTTP/1.1\r\n" + "Host: " + URL + "\r\n" + "Content-Type: " + CONTENTTYPE + "\r\n" + "Connection: close\r\n\r\n" + BODY + "\r\n\r\n";
    //// Making the request.
    client.print(request);
    //// Receiving response headers.
    while (client.connected()) {
      String line = client.readStringUntil('\n');
      // Serial.println("-----HEADERS START-----");
      //Serial.println(line);
      if (line == "\r") {
        // Serial.println("-----HEADERS END-----");
        break;
      }
    }
    // Receiving response body.
    while (client.available()) {
      String line = client.readStringUntil('\n');
      Serial.println(line);
      String greenString = line.substring(line.indexOf("greenVotes\":") + 12, line.indexOf(","));
      Serial.println("greenVotes:" + greenString);
      green = greenString.toInt();
      line = line.substring(line.indexOf("redVotes\":") + 10, line.length() - 1);
      red = line.substring(0, greenString.indexOf(",")).toInt();
      Serial.println("redVotes:" + String(red));
    }
  } else {
    Serial.print("Failed to get votes..");
  }
  if (client.connected()) {
    client.stop();
  }
}

bool pingServer(WiFiClient client) {
  bool successfullConnection = false;
  if (client.connect(server, 8080)) {
    String request = String(METHOD) + " " + "/ping" + " HTTP/1.1\r\n" + "Host: " + URL + "\r\n" + "Content-Type: " + CONTENTTYPE + "\r\n" + "Connection: close\r\n\r\n" + BODY + "\r\n\r\n";
    //// Making the request.
    client.print(request);
    //// Receiving response headers.
    while (client.connected()) {
      String line = client.readStringUntil('\n');
      // Serial.println("-----HEADERS START-----");
      Serial.println(line);
      if (line == "\r") {
        // Serial.println("-----HEADERS END-----");
        break;
      }
    }
    // Receiving response body.
    while (client.available()) {
      String line = client.readStringUntil('\n');
      if(line.indexOf("Success")>=0){
        successfullConnection=true;
        Serial.println("Successfull connection");
      }
      Serial.println(line);
      Serial.println("pingline end");
    }
    if (client.connected()) {
      client.stop();
    }
  }
  return successfullConnection;
}

void incrementGreen(WiFiClient client) {
  String redString = String(0);
  String greenString = String(1);
  String questionIdString = String(questionIds[currentQuestionIdsIndex]);
  String postData = "{\"green\":" + greenString + ",\"red\":" + redString + ",\"questionId\":" + questionIdString + "}";
  Serial.print("postData: ");
  Serial.println(postData);
  if (client.connect(server, 8080)) {
    client.println("POST /vote HTTP/1.1");
    client.println("Host: 192.168.10.157");
    client.println("Content-Type: application/x-www-form-urlencoded");
    client.print("Content-Length: ");
    client.println(postData.length());
    client.println();
    client.print(postData);
  } else {
    Serial.print("Could not vote green");
    printVoteError();
  }
  if (client.connected()) {
    client.stop();
  }
  updateVote(client);
}

void incrementRed(WiFiClient client) {
  String redString = String(1);
  String greenString = String(0);
  String questionIdString = String(questionIds[currentQuestionIdsIndex]);
  String postData = "{\"green\":" + greenString + ",\"red\":" + redString + ",\"questionId\":" + questionIdString + "}";
  Serial.print("postData: ");
  Serial.println(postData);
  if (client.connect(server, 8080)) {
    client.println("POST /vote HTTP/1.1");
    client.println("Host: 192.168.10.157");
    client.println("Content-Type: application/x-www-form-urlencoded");
    client.print("Content-Length: ");
    client.println(postData.length());
    client.println();
    client.print(postData);
  } else {
      Serial.print("Could not vote red");
      printVoteError();
  }

  if (client.connected()) {
    client.stop();
  }
  updateVote(client);
}

void printFocusedNumberLine(LiquidCrystal lcd, int position) {
  lcd.setCursor(0, 1);
  //lcd.createChar(1,upperLine);
  for (int i = 0; i <= 15; i++) {
    if (i == position) {
      lcd.write(byte(1));

      //lcd.write(1);
    } else {
      lcd.print(" ");
    }
  }
}

void printPass(LiquidCrystal lcd) {
  lcd.setCursor(0, 0);
  lcd.print("                 ");
  lcd.setCursor(0, 0);
  lcd.print(pass);
}

void scroll(LiquidCrystal lcd, String text) {
  lcd.setCursor(0, 0);
  lcd.print("                 ");
  lcd.setCursor(0, 0);
  int len = text.length();
  if(len<=16){
    lcd.print(text);
  }else{
    if(scrollCounter>10){
      if(scrollCounter%4==0){
        scrollSpaces++;
      }
      if(scrollSpaces>len-4){
        scrollSpaces=0;
        scrollCounter=0;
      }
    }
    lcd.print(text.substring(scrollSpaces, len));
    scrollCounter++;
  }
}

void printVoteError() {
  lcd.setCursor(0, 1);
  lcd.print("                 ");
  lcd.setCursor(0, 1);
  lcd.print("Vote Failed!");
  delay(2000);
}

void printConnectionStatus(LiquidCrystal lcd, bool success) {
  lcd.setCursor(0, 1);
  lcd.print("                 ");
  lcd.setCursor(0, 1);
  if(success){
  lcd.print("Server ping ok");
  }else{
  lcd.print("Unreachable ip");
  }
}

void printPollid(LiquidCrystal lcd) {
  String pollidString = String(pollid);
  lcd.setCursor(0, 0);
  lcd.print("                 ");
  lcd.setCursor(0, 0);
  lcd.print(pollidString);
}

void printSSID(LiquidCrystal lcd) {
  lcd.setCursor(0, 0);
  lcd.print("                 ");
  lcd.setCursor(0, 0);
  lcd.print(ssid);
}

void updateSSIDString() {
  ssidStringHelper = String(ssid);
  ssidStringHelper.replace(" ", "");
}

void updatePassString() {
  passStringHelper = String(pass);
  passStringHelper.trim();
  Serial.print("e" + passStringHelper + "e");
}

void updatePollidString() {
  pollidStringHelper = String(pollid);
  pollidStringHelper.replace(" ", "");
}

void changeNumberAtIndex(bool increase, int indexOfNumber) {
  char number = pollid[indexOfNumber];
  bool removeNumber = false;
  if (increase) {
    if (number == 57) {  //57 is dec for char 9
      number = 32;
    } else if (!isDigit(number)) {
      number = 48;  //48 is dec for char 0
    } else {
      number++;
    }
  } else {
    if (number == 48) {  //48 is dec for char 0
      number = 32;
    } else if (!isDigit(number)) {
      number = 57;  //57 is dec for char 9
    } else {
      number--;
    }
  }
  char numberChar = number;
  pollid[indexOfNumber] = numberChar;
}

void changeSSIDcharAtIndex(bool increase, int indexOfSSID) {
  char number = ssid[indexOfSSID];
  number = getNextLegalChar(number, increase);
  char numberChar = number;
  ssid[indexOfSSID] = numberChar;
}

void changePasscharAtIndex(bool increase, int indexOfPass) {
  char number = pass[indexOfPass];
  number = getNextLegalChar(number, increase);
  char numberChar = number;
  pass[indexOfPass] = numberChar;
}

char getNextLegalChar(int c, bool increase) {
  //lowercase a-z = 97->122
  //uppercase A-Z = 65->90
  //number 0-9 = 48->57
  //space = 32
  Serial.print("incoming: ");
  Serial.print(String(increase));
  Serial.println(c);

  int number = c;
  if (increase) {
    if (number == 57) {
      number = 97;  //9 is dec for char "A"
    } else if (number == 122) {
      number = 65;  //48 is dec for char 0
    } else if (number == 90) {
      number = 32;  //48 is dec for char 0
    } else if (number == 32) {
      number = 48;  //48 is dec for char 0
    } else {
      number++;
    }
  } else {
    if (number == 97) {
      number = 57;
    } else if (number == 48) {
      number = 32;  //48 is dec for char 0
    } else if (number == 32) {
      number = 90;  //48 is dec for char 0
    } else if (number == 65) {
      number = 122;  //48 is dec for char 0
    } else {
      number--;
    }
  }
  Serial.print("out: ");
  Serial.println(number);
  return char(number);
}

//Return 1 if user presses green or 0 if user presses red
int redGreenDialog(String text, String red, String green, LiquidCrystal lcd) {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(text);
  lcd.setCursor(0, 1);
  lcd.print("g:");
  lcd.print(green);
  lcd.print(" r:");
  lcd.print(red);

  //green = 1, red = 0
  int answer = -1;
  bool answered = false;

  while (!answered) {
    if (digitalRead(BUTTON_PIN_RED) == 0) {
      answer = 0;
      answered = true;
      while (digitalRead(BUTTON_PIN_RED) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_GREEN) == 0) {
      answer = 1;
      answered = true;
      while (digitalRead(BUTTON_PIN_GREEN) == 0) {
        delay(100);
      }
    }
    delay(100);
  }
  return answer;
}

void changeSSID(LiquidCrystal lcd) {
  //green = 1, red = 0
  bool acceptChanges = false;
  int position = 0;
  lcd.clear();
  printSSID(lcd);
  printFocusedNumberLine(lcd, position);

  while (!acceptChanges) {
    printSSID(lcd);
    printFocusedNumberLine(lcd, position);
    if (digitalRead(BUTTON_PIN_LEFT) == 0) {
      if (position == 0) {
        String _ssidDialogText = "ssid:" + String(ssid);
        int ssidCorrect = redGreenDialog(_ssidDialogText, "endre", "ok", lcd);
        if(ssidCorrect==1){
          acceptChanges=true;
        }
      } else {
        position--;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RIGHT) == 0) {
      if (position == 15) {
        String _ssidDialogText = "ssid:" + String(ssid);
        int ssidCorrect = redGreenDialog(_ssidDialogText, "endre", "ok", lcd);
        if (ssidCorrect == 1) {
          acceptChanges = true;
        }
      } else {
        position++;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RED) == 0) {
      changeSSIDcharAtIndex(false, position);
      printSSID(lcd);
      while (digitalRead(BUTTON_PIN_RED) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_GREEN) == 0) {
      changeSSIDcharAtIndex(true, position);
      printSSID(lcd);
      while (digitalRead(BUTTON_PIN_GREEN) == 0) {
        delay(100);
      }
    }
    delay(50);
  }
  return;
}

void changePass(LiquidCrystal lcd) {
  //green = 1, red = 0
  bool acceptChanges = false;
  int position = 0;
  lcd.clear();
  printPass(lcd);
  printFocusedNumberLine(lcd, position);
  delay(500);

  while (!acceptChanges) {
    printPass(lcd);
    printFocusedNumberLine(lcd, position);
    if (digitalRead(BUTTON_PIN_LEFT) == 0) {
      if (position == 0) {
        String _passDialogText = "pw:" + String(pass);
        int passCorrect = redGreenDialog(_passDialogText, "endre", "ok", lcd);
        if(passCorrect==1){
          acceptChanges=true;
        }
      } else {
        position--;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RIGHT) == 0) {
      if (position == 15) {
        String _passDialogText = "pw:" + String(pass);
        int passCorrect = redGreenDialog(_passDialogText, "endre", "ok", lcd);
        if (passCorrect == 1) {
          acceptChanges = true;
        }
      } else {
        position++;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RED) == 0) {
      changePasscharAtIndex(false, position);
      printPass(lcd);
      while (digitalRead(BUTTON_PIN_RED) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_GREEN) == 0) {
      changePasscharAtIndex(true, position);
      printPass(lcd);
      while (digitalRead(BUTTON_PIN_GREEN) == 0) {
        delay(100);
      }
    }
    delay(100);
  }
  return;
}

void changePollid(LiquidCrystal lcd) {
  //green = 1, red = 0
  bool acceptChanges = false;
  int position = 0;
  lcd.clear();
  printPollid(lcd);
  printFocusedNumberLine(lcd, position);
  delay(500);

  while (!acceptChanges) {
    printPollid(lcd);
    printFocusedNumberLine(lcd, position);
    if (digitalRead(BUTTON_PIN_LEFT) == 0) {
      if (position == 0) {
        //Hardwawre is bad and autopresses left_key. Comented this block out until i can get a better breadboard.
        String _pollidDialogText = "pid:" + String(pollid);
        int pollidCorrect = redGreenDialog(_pollidDialogText, "endre", "ok", lcd);
        if (pollidCorrect == 1) {
          acceptChanges = true;
        }
      } else {
        position--;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RIGHT) == 0) {
      if (position == 15) {
        String _pollidDialogText = "pid:" + String(pollid);
        int pollidCorrect = redGreenDialog(_pollidDialogText, "endre", "ok", lcd);
        if (pollidCorrect == 1) {
          acceptChanges = true;
        }
      } else {
        position++;
        printFocusedNumberLine(lcd, position);
      }
      while (digitalRead(BUTTON_PIN_LEFT) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_RED) == 0) {
      changeNumberAtIndex(false, position);
      printPollid(lcd);
      while (digitalRead(BUTTON_PIN_RED) == 0) {
        delay(100);
      }
    } else if (digitalRead(BUTTON_PIN_GREEN) == 0) {
      changeNumberAtIndex(true, position);
      printPollid(lcd);
      while (digitalRead(BUTTON_PIN_GREEN) == 0) {
        delay(100);
      }
    }
    delay(100);
  }
  return;
}
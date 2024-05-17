/*
    Video: https://www.youtube.com/watch?v=oCMOYS71NIU
    Based on Neil Kolban example for IDF: https://github.com/nkolban/esp32-snippets/blob/master/cpp_utils/tests/BLE%20Tests/SampleNotify.cpp
    Ported to Arduino ESP32 by Evandro Copercini

   Create a BLE server that, once we receive a connection, will send periodic notifications.
   The service advertises itself as: 6E400001-B5A3-F393-E0A9-E50E24DCCA9E
   Has a characteristic of: 6E400002-B5A3-F393-E0A9-E50E24DCCA9E - used for receiving data with "WRITE" 
   Has a characteristic of: 6E400003-B5A3-F393-E0A9-E50E24DCCA9E - used to send data with  "NOTIFY"

   The design of creating the BLE server is:
   1. Create a BLE Server
   2. Create a BLE Service
   3. Create a BLE Characteristic on the Service
   4. Create a BLE Descriptor on the characteristic
   5. Start the service.
   6. Start advertising.

   In this example rxValue is the data received (only accessible inside that function).
   And txValue is the data to be sent, in this example just a byte incremented every second. 
*/
#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>
#include <ArduinoJson.h>

BLEServer *pServer = NULL;
BLECharacteristic * pTxCharacteristic;
bool deviceConnected = false;
bool oldDeviceConnected = false;
uint8_t txValue = 0;

int total_time = 30000;

int driver_pin1 = 16;
int red_pin1 = 17;
int black_pin1 = 5;
int driver_pin2 = 21;
int red_pin2 = 18;
int black_pin2 = 19;

int state = 0;

int loop_cond = 0;
unsigned long end_time = 0ul;

// See the following for generating UUIDs:
// https://www.uuidgenerator.net/

#define SERVICE_UUID           "6E400001-B5A3-F393-E0A9-E50E24DCCA9E" // UART service UUID
#define CHARACTERISTIC_UUID_RX "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
#define CHARACTERISTIC_UUID_TX "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"


class MyServerCallbacks: public BLEServerCallbacks {
    void onConnect(BLEServer* pServer) {
      deviceConnected = true;
    };

    void onDisconnect(BLEServer* pServer) {
      deviceConnected = false;
    }
};

void loopfunc(){
  if(millis() >= end_time){
    digitalWrite(red_pin1, LOW);
    digitalWrite(black_pin1, LOW);
    digitalWrite(red_pin2, LOW);
    digitalWrite(black_pin2, LOW);
    loop_cond = 0;
  }
}

class MyCallbacks: public BLECharacteristicCallbacks {
  void onWrite(BLECharacteristic *pCharacteristic) {
    std::string rxValue = pCharacteristic->getValue();

    if (rxValue.length() > 0) {
      Serial.println("*********");
      Serial.print("Received Value: ");
      for(int i = 0; i < rxValue.length(); i++){
        Serial.print(rxValue[i]);
      }
      Serial.println();
      Serial.println("*********");

      JsonDocument root;
      deserializeJson(root, rxValue);
      std::string request_name = root["requestName"];
      if(request_name.compare("getId") == 0){
        root["machineType"] = "curtain";
        String output;
        serializeJson(root, output);
        pTxCharacteristic->setValue((uint8_t*)output.c_str(), output.length());
        pTxCharacteristic->notify();
      }
      else{
        if(request_name.compare("setState") == 0){
          int new_state = root["state"];
          unsigned long cur_time = 0ul;

          if(new_state > state){
            digitalWrite(red_pin1, LOW);
            digitalWrite(black_pin1, HIGH);
            digitalWrite(red_pin2, LOW);
            digitalWrite(black_pin2, HIGH);
            cur_time = total_time * (new_state - state) / 100;
          }
          else if(new_state < state){
            digitalWrite(red_pin1, HIGH);
            digitalWrite(black_pin1, LOW);
            digitalWrite(red_pin2, HIGH);
            digitalWrite(black_pin2, LOW);
            cur_time = total_time * (state - new_state) / 100;
          };
          end_time = millis() + cur_time;
          loop_cond = 1;

          state = new_state;
        }
        else if(request_name.compare("getState") == 0){
          root["state"] = state;
          String output;
          serializeJson(root, output);
          pTxCharacteristic->setValue((uint8_t*)output.c_str(), output.length());
          pTxCharacteristic->notify();
        }
      }
    }
  }
};


void setup() {
  Serial.begin(115200);

  // Create the BLE Device
  BLEDevice::init("Curtain");

  // Create the BLE Server
  pServer = BLEDevice::createServer();
  pServer->setCallbacks(new MyServerCallbacks());

  // Create the BLE Service
  BLEService *pService = pServer->createService(SERVICE_UUID);

  // Create a BLE Characteristic
  pTxCharacteristic = pService->createCharacteristic(
										CHARACTERISTIC_UUID_TX,
										BLECharacteristic::PROPERTY_NOTIFY
									);
                      
  pTxCharacteristic->addDescriptor(new BLE2902());

  BLECharacteristic * pRxCharacteristic = pService->createCharacteristic(
											 CHARACTERISTIC_UUID_RX,
											BLECharacteristic::PROPERTY_WRITE
										);

  pRxCharacteristic->setCallbacks(new MyCallbacks());

  // Start the service
  pService->start();

  // Start advertising
  pServer->getAdvertising()->start();
  Serial.println("Waiting a client connection to notify...");
  
  // linear motor pin setting
  pinMode(driver_pin1, OUTPUT);
  pinMode(red_pin1, OUTPUT);
  pinMode(black_pin1, OUTPUT);
  pinMode(driver_pin2, OUTPUT);
  pinMode(red_pin2, OUTPUT);
  pinMode(black_pin2, OUTPUT);
}

void loop() {
  if (deviceConnected) {
    // if(Serial.available()){
    //   String input = Serial.readString();
    //   digitalWrite(red_pin1, HIGH);
    //   digitalWrite(black_pin1, LOW);
    //   delay(35000);
    //   int degree = input.toInt();
    //         digitalWrite(red_pin1, LOW);
    //         digitalWrite(black_pin1, HIGH);
    //         delay(degree);
    //         digitalWrite(red_pin1, LOW);
    //         digitalWrite(black_pin1, LOW);
    //   // pTxCharacteristic->setValue((uint8_t*)input.c_str(), input.length());
    //   // pTxCharacteristic->notify();
    // }
    if(loop_cond){
      loopfunc();
    }
  }

    // disconnecting
    if (!deviceConnected && oldDeviceConnected) {
        delay(500); // give the bluetooth stack the chance to get things ready
        pServer->startAdvertising(); // restart advertising
        Serial.println("start advertising");
        oldDeviceConnected = deviceConnected;
        digitalWrite(driver_pin1, LOW);
        digitalWrite(driver_pin2, LOW);
        loop_cond = 0;
    }
    // connecting
    if (deviceConnected && !oldDeviceConnected) {
		// do stuff here on connecting
        Serial.println("device connecting");
        oldDeviceConnected = deviceConnected;
        
        digitalWrite(driver_pin1, HIGH);
        digitalWrite(driver_pin2, HIGH);

        digitalWrite(red_pin1, HIGH);
        digitalWrite(black_pin1, LOW);
        digitalWrite(red_pin2, HIGH);
        digitalWrite(black_pin2, LOW);
        end_time = millis() + 31000;
        loop_cond = 1;

        state = 0;
    }
}
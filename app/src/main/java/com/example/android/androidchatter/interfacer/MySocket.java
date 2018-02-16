package com.example.android.androidchatter.interfacer;

/**
 * Created by Speed on 14/02/2018.
 */

public interface MySocket {
    String sendHTTPRequest(String params);
    int startListeningPort(int port);

   // void stopListening();
    //void exit();
}

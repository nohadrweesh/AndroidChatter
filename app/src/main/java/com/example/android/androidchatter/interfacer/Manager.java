package com.example.android.androidchatter.interfacer;

import java.io.UnsupportedEncodingException;

/**
 * Created by Speed on 13/02/2018.
 */

public interface Manager {
    String getUsername();
    String sendMessage(String username,String toUsername,String Message) throws UnsupportedEncodingException;

    String authintecateUser(String username,String password)throws UnsupportedEncodingException;

    void messageReceived(String username,String message);

    boolean isNetworkConnected();
    boolean isUserAuthenticated();
    String getLastRawFriendList();
    void exit();
    String signUpUser(String username,String password,String email);
    String addNewFriendRequest(String approvedFriendNames);
    String sendFriendRequest(String approvedFriendNames,String discardedFriendName);

}

package com.example.android.androidchatter.serve;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.androidchatter.comm.Socketer;
import com.example.android.androidchatter.interfacer.Manager;
import com.example.android.androidchatter.interfacer.Updater;
import com.example.android.androidchatter.typo.InfoOfFriends;
import com.example.android.androidchatter.typo.InfoOfMessage;

import java.io.UnsupportedEncodingException;
import java.util.Timer;

/**
 * Created by Speed on 14/02/2018.
 */

public class MessagingService <InfoOfFriend>extends Service implements
        Manager,Updater{
    public static  String USERNAME;
    public static final String TAKE_MESSAGE="Take_Message";
    public static final String FRIEND_LIST_UPDATED="Take_Friend_List";
    public static final String MESSAGE_LIST_UPDATED="Take_Message_List";
    public ConnectivityManager conManager=null;
    private final int UPDATE_TIME_PERIOD=15000;
    private String rawFriendList=new String();
    private String rawMessageList=new String();

    Socketer socketOperator=new Socketer();

    private final  IBinder mBinder=new IMBinder() ;
    private String username;
    private String password;
    private boolean authenticateedUser=false;
    private Timer timer;






    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String sendMessage(String username, String toUsername, String Message) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public String authintecateUser(String username, String password) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public void messageReceived(String username, String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public boolean isUserAuthenticated() {
        return false;
    }

    @Override
    public String getLastRawFriendList() {
        return null;
    }

    @Override
    public void exit() {

    }

    @Override
    public String signUpUser(String username, String password, String email) {
        return null;
    }

    @Override
    public String addNewFriendRequest(String approvedFriendNames) {
        return null;
    }

    @Override
    public String sendFriendRequest(String approvedFriendNames, String discardedFriendName) {
        return null;
    }

    @Override
    public void updateData(InfoOfMessage[] message, InfoOfFriends[] friends, InfoOfFriends[] unApprovedFriends, String userKey) {

    }
}

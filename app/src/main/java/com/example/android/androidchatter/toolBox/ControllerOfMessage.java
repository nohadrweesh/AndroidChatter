package com.example.android.androidchatter.toolBox;

import com.example.android.androidchatter.typo.InfoOfMessage;

/**
 * Created by Speed on 14/02/2018.
 */

public class ControllerOfMessage {
    public static final String taken="taken";
    public static InfoOfMessage[]infoOfMessages=null;

    public static void setMessageInfo(InfoOfMessage [] info){
        infoOfMessages=info;
    }

    public static  InfoOfMessage checkMessage(String username){
        InfoOfMessage result=null;
        for(int i=0;i<infoOfMessages.length;i++){
            result=infoOfMessages[i];
            break;
        }
        return result;
    }

    public static InfoOfMessage[] getMessages(){
        return infoOfMessages;
    }
}

package com.example.android.androidchatter.toolBox;

import com.example.android.androidchatter.typo.InfoOfFriends;

/**
 * Created by Speed on 14/02/2018.
 */

public class ControllerOfFriend {
    public static InfoOfFriends[]friendsInfo;
    public static InfoOfFriends[]unapprovedFriends;
    public String activeFriends;

    public static void setFriendsInfo(InfoOfFriends[]friends){
        friendsInfo=friends;
    }
    public static InfoOfFriends checkFriends(String username,String userKey){
        InfoOfFriends result=null;
        if(friendsInfo!=null){
            for(int i=0;i<friendsInfo.length;i++){
                if(friendsInfo[i].Username.equals(username)&&friendsInfo[i].User_Key.equals(userKey)){
                    result=friendsInfo[i];
                    break;
                }
            }
        }
        return result;

    }

    public static InfoOfFriends  getFriendsInfo(String username){
        InfoOfFriends result=null;
        if(friendsInfo!=null){
            for(int i=0;i<friendsInfo.length;i++){
                if(friendsInfo[i].Username.equals(username)){
                    result=friendsInfo[i];
                    break;
                }
            }
        }
        return result;
    }

}

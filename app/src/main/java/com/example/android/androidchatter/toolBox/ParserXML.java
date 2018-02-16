package com.example.android.androidchatter.toolBox;

import com.example.android.androidchatter.interfacer.Updater;
import com.example.android.androidchatter.typo.InfoOfFriends;
import com.example.android.androidchatter.typo.InfoStatus;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Vector;

/**
 * Created by Speed on 14/02/2018.
 */

public class ParserXML extends DefaultHandler {

    public String userKey=new String();
    public Updater updater;

    public  ParserXML(Updater updater){
        this.updater=updater;
    }

    private Vector<InfoOfFriends> mFriends=new Vector<InfoOfFriends>();
    private Vector<InfoOfFriends> mOnlineFriends=new Vector<InfoOfFriends>();
    private Vector<InfoOfFriends> mUnapprovedFriends=new Vector<InfoOfFriends>();

    private Vector<Updater>mUreadMessages=new Vector<Updater>();

    public void endDocument()throws SAXException
    {
        InfoOfFriends[]friends=new InfoOfFriends[mFriends.size()+mOnlineFriends.size()];
        Updater[]messages=new Updater[mUreadMessages.size()];

        int onlineFriendCount=mOnlineFriends.size();
        for(int i=0;i<onlineFriendCount;i++){
            friends[i]=mOnlineFriends.get(i);
        }

        int offlineFriendCount=mFriends.size();
        for(int i=0;i<offlineFriendCount;i++){
            friends[i+onlineFriendCount]=mFriends.get(i);
        }

        int unapprovedFriendsList=mUnapprovedFriends.size();
        for(int i=0;i<unapprovedFriendsList;i++){
            messages[i]=mUreadMessages.get(i);

        }
    }

    public void startElement(String uri, String localName, String name, Attributes attributes)throws SAXException
    {
        if(localName=="friend"){
            InfoOfFriends friend=new InfoOfFriends();
            friend.Username=attributes.getValue(InfoOfFriends.Username);
            String status=attributes.getValue(InfoOfFriends.Status);

            friend.Port=attributes.getValue(InfoOfFriends.Port);

            if(status!=null && status.equals("online")){
                friend.Status = InfoStatus.ONLINE;
                mOnlineFriends.add(friend);

            }
            else if(status.equals("unApproved")){
                friend.Status=InfoStatus.UNAPPROVED;
                mUnapprovedFriends.add(friend);
            }else{
                friend.Status=InfoStatus.OFFLINE;
                mFriends.add(friend);
            }
        }

    }


}

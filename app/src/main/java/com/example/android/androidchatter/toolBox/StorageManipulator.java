package com.example.android.androidchatter.toolBox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Speed on 14/02/2018.
 */

public class StorageManipulator extends SQLiteOpenHelper {
    public static final String Database_name="AndroidChatter.db";
    public static final int Database_version=1;

    public static final String _ID="_id";
    public static final String Table_name_message="table_message";
    public static final String Message_Receiver="receiver";
    public static final String Message_Sender="Sender";
    public static final String Message_Message="message";

    public static final String TABLE_MESSAGE_CREATE
            ="CREATE TABLE "+ Table_name_message
            +" ( "+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +Message_Receiver+" VARCHAR(25), "
            +Message_Sender+" VARCHAR(25);";

    public static final String TABLE_MESSAGE_DROP
            ="DROP TABLE IF EXISTS "+Table_name_message;

    public StorageManipulator(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_MESSAGE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TABLE_MESSAGE_DROP);
        //TODO (1)handle

    }

    public void insert(String Sender,String Receiver,String Message){
        long rowId;
        try{
            SQLiteDatabase db=getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(Message_Receiver,Receiver);
            cv.put(Message_Sender,Sender);
            cv.put(Message_Message,Message);
            rowId=db.insert(Table_name_message,null,cv);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public Cursor get(String sender,String Receiver){
        SQLiteDatabase db=getWritableDatabase();
        String selectQuery="SELECT FROM "+Table_name_message
                +" WHERE "+Message_Sender+" LIKE "+sender
                +" AND "+Message_Receiver+"LIKE "+Receiver
                + " ASC;";

        return null;
    }
}

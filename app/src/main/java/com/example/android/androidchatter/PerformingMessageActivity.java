package com.example.android.androidchatter;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidchatter.interfacer.Manager;
import com.example.android.androidchatter.serve.MessagingService;
import com.example.android.androidchatter.toolBox.StorageManipulator;
import com.example.android.androidchatter.typo.InfoOfFriends;
import com.example.android.androidchatter.typo.InfoOfMessage;

import java.io.UnsupportedEncodingException;

public class PerformingMessageActivity extends AppCompatActivity {

    public static final int MESSAGE_NOT_SENT=0;

    public TextView messageBox;
    public EditText sendMessage;
    public Button send;

    public Manager serviceProvider;
    public Cursor cursor;

    public InfoOfFriends friends=new InfoOfFriends();
    public StorageManipulator localDataStorage;
    @SuppressWarnings("unused")
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            serviceProvider=((MessagingService.IMBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceProvider=null;
            Toast.makeText(getApplicationContext(),"local service stopped",Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performing_message);
        messageBox=(TextView)findViewById(R.id.messageHistoryBox);
        sendMessage=(EditText)findViewById(R.id.sendMessage);
        sendMessage.requestFocus();
        send=(Button)findViewById(R.id.send);
        Bundle extras =this.getIntent().getExtras();

        friends.userName=extras.getString(InfoOfFriends.Username);
        friends.ip=extras.getString(InfoOfFriends.IP);
        friends.port=extras.getString(InfoOfFriends.Port);
        String msg=extras.getString(InfoOfFriends.Message);

    setTitle("Messaging with "+friends.userName);
    localDataStorage=new StorageManipulator(this,msg,null,0);
    cursor=localDataStorage.get(friends.userName,MessagingService.USERNAME);

    if(cursor.getCount()>0){
        int noOfScorer=0;
        cursor.moveToFirst();
        while ((!cursor.isAfterLast())&&noOfScorer<cursor.getCount()){
            noOfScorer++;
            this.appendToMessageHistory(cursor.getString(2),cursor.getString(3));
        }
    }
    localDataStorage.close();
    if(msg!=null){
        this.appendToMessageHistory(friends.userName,msg);
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel((friends.userName.hashCode()));


    }
    send.setOnClickListener(new View.OnClickListener() {
        CharSequence message;
        Handler handler=new Handler();
        @Override
        public void onClick(View view) {
            message=sendMessage.getText();
            if(message.length()>0){
                appendToMessageHistory(serviceProvider.getUsername(),message.toString());
                localDataStorage.insert(serviceProvider.getUsername(),friends.userName,message.toString());
                sendMessage.setText("");
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        try{
                            if(serviceProvider.sendMessage(serviceProvider.getUsername(),friends.userName,message.toString())==null){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Message can't be sent ",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(),"Message can't be sent ",Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }

        }
    });

    }

    public class MessgeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras=intent.getExtras();

            String username=extras.getString(InfoOfMessage.UserId);
            String message =extras.getString(InfoOfMessage.MessageText);
            if(username!=null&&message!=null){
                if(friends.userName.equals(username)){
                    appendToMessageHistory(username,message);
                    localDataStorage.insert(username,InfoOfFriends.Username,message);
                }
            }
        }
    }
    public MessgeReceiver messgeReceiver=new MessgeReceiver();

    private void appendToMessageHistory(String username,String message){

        if(username!=null && message!=null){
            messageBox.append(username+": \n");
            messageBox.append(message+": \n");
        }
    }
}

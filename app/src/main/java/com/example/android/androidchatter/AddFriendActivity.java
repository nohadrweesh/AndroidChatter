package com.example.android.androidchatter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidchatter.interfacer.Manager;
import com.example.android.androidchatter.serve.MessagingService;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText username;
    public Button Add;
    public Button Cancel;

    public static final int TYPE_FRIEND_USERNAME=0;
    public Manager serviceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setTitle("Add new Friend");

        Add=(Button)findViewById(R.id.addFriend);
        Cancel=(Button)findViewById(R.id.cancel);
        username=(EditText)findViewById(R.id.newFriendUsername);

        if(Add!=null){
            Add.setOnClickListener(this);
        }
        if(Cancel!=null){
            Cancel.setOnClickListener(this);
        }

    }
    @SuppressWarnings("unused")
    private final ServiceConnection mConnection=new ServiceConnection() {
        @SuppressWarnings("rawtypes")
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            serviceProvider=((MessagingService.IMBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if(serviceProvider!=null){
                serviceProvider=null;
            }
            Toast.makeText(getApplicationContext(),"local service stopped",Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View view) {
        if(view==Cancel){
            finish();
        }else if(view==Add){
            addNewFriend();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(AddFriendActivity.this);
        if(id==TYPE_FRIEND_USERNAME){
            builder.setTitle("Add new friend")
                    .setMessage("Friend Usernae");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
            return  builder.create();
    }

    public void addNewFriend(){
        if(username.length()>0){
            Thread thread=new Thread(){
                @Override
                public void run() {
                    serviceProvider.addNewFriendRequest(username.getText().toString());
                }
            };
            thread.start();
            Toast.makeText(getApplicationContext(),"Request sent",Toast.LENGTH_SHORT).show();
            finish();
        }



    }
}

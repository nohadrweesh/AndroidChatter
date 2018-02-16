package com.example.android.androidchatter;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidchatter.interfacer.Manager;
import com.example.android.androidchatter.serve.MessagingService;

import java.io.UnsupportedEncodingException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    public static final int CONNECTED_TO_SERVICE=0;
    public static final int FILL_BOTH_USERNAME_AND_PASSWORD=0;
    public static final String Authentical_Failed="failed";
    public static final String Friend_List="friend-list";
    public static final int MAKE_SURE_USERNAME_PASSWORD=2;
    public static final int connected_TO_NETWORK=3;

    public EditText username;
    public EditText password;

    public Manager serviceProvider;

    public static final int SignUp= Menu.FIRST;
    public static final int Exit=Menu.FIRST;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(LoginActivity.this,MessagingService.class));
        setContentView(R.layout.activity_login);
        setTitle("Login");

        Button loginBtn=(Button)findViewById(R.id.email_sign_in_button);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.email);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serviceProvider==null){
                    Toast.makeText(getApplicationContext(),"Not connected to service",Toast.LENGTH_SHORT).show();
                    return;
                }else if(serviceProvider.isNetworkConnected()==false){
                    Toast.makeText(getApplicationContext(),"Not connected to service",Toast.LENGTH_SHORT).show();

                }else if(username.length()>0 && password.length()>0){
                    Thread loginThread=new Thread(){
                        private Handler handler=new Handler();

                        @Override
                        public void run() {
                            String result=null;
                            try{
                                result=serviceProvider.authintecateUser(username.getText().toString(),
                                        password.getText().toString());
                            }catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            }
                            if (result == null || result.equals(Authentical_Failed)) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"make sure username and pass are correct ",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }else{
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i=new Intent(LoginActivity.this,ListOfFriendsActivity.class);
                                        startActivity(i);
                                        LoginActivity.this.finish();
                                    }
                                });
                            }
                        }
                    };
                    loginThread.start();
                }else{
                    Toast.makeText(getApplicationContext(),"fill in username and pass ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case connected_TO_NETWORK:
                break;
            case CONNECTED_TO_SERVICE:
                break;
            case FILL_BOTH_USERNAME_AND_PASSWORD:
                break;
        }
    }

    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceProvider=((MessagingService.IMBinder)iBinder).getService();
            if(serviceProvider.isNetworkConnected()==true){
                Intent i=new Intent(LoginActivity.this,ListOfFriendsActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        }


        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceProvider=null;
            Toast.makeText(LoginActivity.this,R.string.local_service_stopped,
                    Toast.LENGTH_SHORT).show();


        }
    };

}


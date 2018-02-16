package com.example.android.androidchatter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidchatter.interfacer.Manager;

public class SignUpActivity extends AppCompatActivity {

    public static final int FILL_ALL_FIELD=0;
    public static final int TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS=1;
    public static final int SIGNUP_FAILED=2;
    public static final int SIGNUP_SUCCESSFUL=3;
    public static final int USERNAME_PASSWORD_LENGTH_SHORT=4;

    public static final String SERER_SIGNUP_SUCCESSFUL="5";
    public static final String SERER_SIGNUP_CRASHED="6";

    public EditText username;
    public EditText password;
    public EditText password_confirm;
    public EditText email ;

    public Manager serviceProvider;
    public Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        Button signupBtn=(Button)findViewById(R.id.signup);
        Button cancelBtn=(Button)findViewById(R.id.cancel);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        password_confirm=(EditText)findViewById(R.id.password_confirm);
        email=(EditText)findViewById(R.id.email);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.length()>0&&email.length()>0
                        &&password.length()>0&& password_confirm.length()>0){
                    if(password.getText().toString()==password_confirm.getText().toString()){
                        Thread thread=new Thread(){
                            String result=new String();
                            @Override
                            public void run() {
                                result=serviceProvider.signUpUser(username.getText().toString(),
                                        password.getText().toString(),
                                        email.getText().toString());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.equals(SERER_SIGNUP_SUCCESSFUL)) {
                                            Toast.makeText(getApplicationContext(),"sign up sucessful",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"sign up failed",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


                            }
                        };
                        thread.start();
                    }else{
                        Toast.makeText(getApplicationContext(),"confirm your pass ",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"fill in your data ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case TYPE_SAME_PASSWORD_IN_PASSWORD_FIELDS:
                return new AlertDialog.Builder(this)
                        .setMessage("type same password")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
            case FILL_ALL_FIELD:
                return new AlertDialog.Builder(this)
                        .setMessage("FIll all fields")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
            case SIGNUP_FAILED:
                return new AlertDialog.Builder(this)
                        .setMessage("Sign up failed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                default:
                    return null;
        }
    }
}

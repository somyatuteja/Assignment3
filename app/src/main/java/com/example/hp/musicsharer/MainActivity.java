package com.example.hp.musicsharer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static final String TAG="username";
    public static final String DEFAULT="Username";

    void goToLoggedInActivity()
    {
        Intent intent = new Intent(getBaseContext(),LoggedInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity","HERE1");
        Button mLoginButton = (Button)findViewById(R.id.loginbutton);
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        Log.v("MainActivity","HERE2");
        String user= sharedPreferences.getString(TAG,DEFAULT);
        if(user.equals(DEFAULT))
        {
            Log.v("MainActivity","HERE3");
        }
        else
        {
            Log.v("MainActivity","HERE4");
            goToLoggedInActivity();
        }

        try {
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MainActivity","HERE5");
                    SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    EditText mUsernameEditText =(EditText)findViewById(R.id.munameedittext);
                    editor.putString(TAG,mUsernameEditText.getText().toString());
                    editor.commit();
                    Log.v("MainActivity","HERE6");
                    goToLoggedInActivity();
                }
            });
        } catch (Exception e){}

    }
}

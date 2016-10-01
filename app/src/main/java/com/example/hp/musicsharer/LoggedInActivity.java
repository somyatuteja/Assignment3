
package com.example.hp.musicsharer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedInActivity extends AppCompatActivity {
    public static final String TAG="username";
    public static final String DEFAULT="Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        TextView musernametextview = (TextView)findViewById(R.id.usernametextview);
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        String user= sharedPreferences.getString(TAG,DEFAULT);
        musernametextview.setText(" "+user);
        Button mLogoutButton = (Button)findViewById(R.id.logoutbutton);
        try {
            mLogoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Context context=getApplicationContext();
                    Toast toast=Toast.makeText(context,"Logging you out",Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e){}


    }


}

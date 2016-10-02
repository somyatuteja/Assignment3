
package com.example.hp.musicsharer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
    public static final String DEFAULT="Guest";
    public static  DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        db=DatabaseHelper.getInstance(context);
        setContentView(R.layout.activity_logged_in);
        TextView musernametextview = (TextView)findViewById(R.id.usernametextview);
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        final String user= sharedPreferences.getString(TAG,DEFAULT);
        musernametextview.setText(" "+user);
        Button mLogoutButton = (Button)findViewById(R.id.logoutbutton);
        try {
            mLogoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Logging you out", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){}
        Button mProfileButton = (Button)findViewById(R.id.viewprofile);
        try {
            mProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context=getApplicationContext();

                    Cursor res=db.getData();
                    if(res.getCount()==0)
                    {
                        Toast toast=Toast.makeText(context,"No Data yet",Toast.LENGTH_LONG);
                        toast.show();
                    }
                    StringBuffer buffer =new StringBuffer();
                    while(res.moveToNext())
                    {
                        buffer.append("ID :"+res.getString(0)+"\n");
                        buffer.append("UserName :"+res.getString(1)+"\n");
                        buffer.append("Song Name :"+res.getString(2)+"\n\n");
                    }
                    EditText mProfileEditText =(EditText)findViewById(R.id.profileEditText);
                    mProfileEditText.setText(buffer);
                }
            });
        }catch (Exception e){}
            Button mMergeButton = (Button)findViewById(R.id.mergemusic);
            try {
                mMergeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Context context=getApplicationContext();
                        Toast toast=Toast.makeText(context,"Going to Merge",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getBaseContext(),MergeMusic.class);

                        startActivity(intent);

                    }
                });
        } catch (Exception e){}
        final Button mDeleteButton = (Button)findViewById(R.id.deletebutton);
        final EditText mDeleteEditText =(EditText)findViewById(R.id.deleteet);
        try {
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.deleteData(mDeleteEditText.getText().toString());
                }
            });
        } catch (Exception e){}


    }


}

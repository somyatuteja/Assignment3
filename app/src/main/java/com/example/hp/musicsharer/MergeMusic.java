package com.example.hp.musicsharer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MergeMusic extends AppCompatActivity {
    File f1=null;
    File f2=null;
    static FileInputStream fisf1;
    static FileInputStream fisf2;
    private static final int GET_F1 = 0;
    private static final int GET_F2 = 2;
    private void getFileFromStorage(int l) {

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        if(l==1)
        startActivityForResult(i, GET_F1);
        if(l==2)
            startActivityForResult(i, GET_F2);
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == GET_F1) {
                    Uri selectedmp3 = data.getData();
                    try {
                        fisf1 = (FileInputStream)getContentResolver().openInputStream(selectedmp3);
                    }
                    catch (Exception e)
                    {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context,"OOPs some error , try again", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    f1 = new File((selectedmp3.toString()));
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, f1.getName(), Toast.LENGTH_LONG);
                    toast.show();

                }
                if (requestCode == GET_F2) {
                    Uri selectedmp3 = data.getData();
                    f2 = new File((selectedmp3.toString()));

                    try {
                        fisf2 = (FileInputStream)getContentResolver().openInputStream(selectedmp3);
                    }
                    catch (Exception e)
                    {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context,"OOPs some error , try again", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context,f2.getName(), Toast.LENGTH_LONG);
                    toast.show();
                }

            }}


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_music);
        Button mFile1 = (Button) findViewById(R.id.selectfilefirst);
        final TextView mNewNameTextView =(TextView)findViewById(R.id.filenewnameet);
        Log.v("MergeMusicActivity", "Here1");
        try {
            mFile1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MergeMusicActivity", "Here2");
                    getFileFromStorage(1);
                }
            });
        } catch (Exception e) {
        }

        Button mFile2 = (Button) findViewById(R.id.selectfilesecond);
        try {
            mFile2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MergeMusicActivity", "Here5");
                    getFileFromStorage(2);
                }
            });
        } catch (Exception e) {
        }
        Button mCheck = (Button) findViewById(R.id.checkinternalforfile);
        try {
            mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/merge.mp3";
                        File file = new File(path);
                        if(file.exists())
                        {
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "Exist", Toast.LENGTH_SHORT);
                            toast.show();
                    }
                    if(!file.exists())
                    {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Doesnt Exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });
        } catch (Exception e) {
        }
        Button mMergeExternalButton = (Button) findViewById(R.id.mergeexternal);
        try {
            mMergeExternalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((f1 == null) || (f2 == null)) {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Select file", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {

                         String state = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            try {
                                Log.v("Merged","Media mounted");
                                File f= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                                Log.v("Merged","ExternalStoragePublicDirectory");
                            File file= new File(f,mNewNameTextView.getText().toString()+".mp3");
                                Log.v("Merged","created");
                                FileOutputStream fos = null;
                                try {
                                    fos = new FileOutputStream(file,true);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            byte[] b= new byte[1000];
                            int a;


                                    FileInputStream fis1 ;

                                    while((a=fisf1.read(b))!=-1)
                                    {
                                        fos.write(b);
                                        Log.v("Merge","copying 1");
                                    }

                            while((a=fisf2.read(b))!=-1)
                            {
                                fos.write(b);
                                Log.v("Merge","copying 2");
                            }
                            fos.close();
                                Log.v("Merge","Done");
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, "Done", Toast.LENGTH_LONG);
                                toast.show();
                                }
                                 catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        else
                        {
                            Context context = getApplicationContext();

                            Toast toast = Toast.makeText(context, "Cant write", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                }
            });
        } catch (Exception e) {

        }
    Button mMergeInternalButton =(Button)findViewById(R.id.mergeinternal);
    try {
        mMergeInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((f1==null )||( f2==null))
                {
                    Context context=getApplicationContext();
                    Toast toast=Toast.makeText(context,"Select file",Toast.LENGTH_SHORT);
                    toast.show();                }
                else                {
          String FILENAME = mNewNameTextView.getText().toString()+".mp3";
          try {
          FileOutputStream fos = getApplicationContext().openFileOutput(FILENAME, getApplicationContext().MODE_PRIVATE);
              FileInputStream fis1 = new FileInputStream(f1);
              byte[] b= new byte[1000];
              int a;
              while((a=fis1.read(b))!=-1)
              {                  fos.write(b);              }
              FileInputStream fis2 = new FileInputStream(f1);
              while((a=fis2.read(b))!=-1)
              {                  fos.write(b);              }
    fos.close();
          }
catch (Exception e){}
      }            }

        });
    } catch (Exception e){}
        }
}

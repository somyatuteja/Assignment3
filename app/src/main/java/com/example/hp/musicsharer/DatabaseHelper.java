package com.example.hp.musicsharer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by HP on 10/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME="UserDetails.db";
    public static String TABLE_NAME="UserTable";
    public static String COL1="_id";
    public static String COL2="User";
    public static String COL3="Filename";
    private static DatabaseHelper dbIsntance;
    public static DatabaseHelper getInstance(Context context){
        if(dbIsntance==null){
            dbIsntance= new DatabaseHelper(context);
        }
        return dbIsntance;
    }
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create table "+TABLE_NAME+"("+COL1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL2+" VARCHAR,"+COL3+" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("Drop table if exists "+DB_NAME);
        onCreate(db);
    }
    public void insertData(String uname, String fname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL2,uname);
        contentValues.put(COL3,fname);
        long result= db.insert(TABLE_NAME,null,contentValues);

    }
    public Cursor getData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+TABLE_NAME,null);
        return res;
    }
}

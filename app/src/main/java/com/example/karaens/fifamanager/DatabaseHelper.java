package com.example.karaens.fifamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Backup.db";
    public static final String TABLE_NAME="Fix_Table";
    public static final String COL1="ID";
    public static final String COL2="TEAM_1";
    public static final String COL3="TEAM_2";
    public static final String COL4="DATE";
    public static final String COL5="TIME";
    public static final String COL6="VENUE";
    public static final String COL7="TEAM_ICON1";
    public static final String COL8="TEAM_ICON2";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TEAM_1 TEXT,TEAM_2 TEXT,DATE TEXT," +
                   "TIME TEXT,VENUE TEXT,TEAM_ICON1 BLOB,TEAM_ICON2 BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    boolean addData(String id,String t1,String t2,String date,String time,String venue,byte[] icon1,byte[] icon2){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,t1);
        contentValues.put(COL3,t2);
        contentValues.put(COL4,date);
        contentValues.put(COL5,time);
        contentValues.put(COL6,venue);
        contentValues.put(COL7,icon1);
        contentValues.put(COL8,icon2);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    Cursor getData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    boolean updateData(String id,String t1,String t2,String date,String time,String venue,byte[] icon1,byte[] icon2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, t1);
        contentValues.put(COL3, t2);
        contentValues.put(COL4, date);
        contentValues.put(COL5, time);
        contentValues.put(COL6, venue);
        contentValues.put(COL7, icon1);
        contentValues.put(COL8, icon2);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] { id });
        return true;
    }

    int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int res=db.delete(TABLE_NAME,"ID=?",new String[] { id });
        return res;
    }

    int resetData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
        Cursor rows=getData();
        if(rows.getCount()==0){
            return 0;
        }
        else
            return 1;
    }

}


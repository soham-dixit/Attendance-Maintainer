package com.groupsix.attendancemaintainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Attendance.db";
    public static final String TABLE_NAME = "attendance";
    public static final String COL1 = "id";
    public static final String COL2 = "subject_name";
    public static final String COL3 = "presents";
    public static final String COL4 = "absents";
    public static final String COL5 = "current_percent";
    public static final String COL6 = "minimum_percent";
    public static final String username= "username";
    public static final String password = "password";

    public DataBaseHelper(Context context){super(context, TABLE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+" TEXT, "+COL3+" TEXT, "+COL4+" TEXT, "+COL5+" TEXT, "+COL6+" TEXT)");
        db.execSQL("CREATE TABLE credentials(id INTEGER PRIMARY KEY AUTOINCREMENT, "+username+" TEXT, "+password+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public long insertData(String name, String minimum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COL2,name);
        value.put(COL3,"0");
        value.put(COL4,"0");
        value.put(COL5,"0");
        value.put(COL6,minimum);
        long result=db.insert(TABLE_NAME,null,value);
        return result;
    }

    public Long insertCredentials(String username, String password){
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDb.insert("credentials", null, contentValues);
        return result;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from credentials where username=?", new String[] {username});
        if (cursor.getCount() > 0) {
            return true;
        }else{
            return  false;
        }
    }
    public Boolean matchuserpass(String username , String password){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from credentials where username=? and password=?", new String[] {username,password});
        if (cursor.getCount() > 0) {
            return true;
        }else{
            return  false;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db=this.getWritableDatabase();
        int x=db.delete(TABLE_NAME," ID=? ",new String[]{id});
        return x;
    }
    public boolean update_presents(String id,String presents,String current) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL1,id);
        value.put(COL3,presents);
        value.put(COL5,current);
        db.update(TABLE_NAME,value," ID = ?",new String []{id});
        return true;
    }
    public boolean update_absents(String id,String absents,String current) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL1,id);
        value.put(COL4,absents);
        value.put(COL5,current);
        db.update(TABLE_NAME,value," ID = ?",new String []{id});
        return true;
    }
    public boolean modify(String id,String subject,String presents,String absents,String current,String minimum) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL1,id);
        value.put(COL2,subject);
        value.put(COL3,presents);
        value.put(COL4,absents);
        value.put(COL5,current);
        value.put(COL6,minimum);
        db.update(TABLE_NAME,value," ID = ?",new String []{id});
        return true;
    }
}
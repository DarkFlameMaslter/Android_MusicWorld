package com.example.musicworldfinal.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private String TAG = "SQLI";

    public static final String DatabaseName = "MusicWorld";
    public static final String UserTableName = "User";
    public static final String UserColumn_1 = "id";
    public static final String UserColumn_2 = "username";
    public static final String UserColumn_3 = "UserLoginId";
    public static final String UserColumn_4 = "UserLoginPassword";
    public static final String UserColumn_5 = "UserPriv";

    public DBHelper(@Nullable Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ UserTableName +" ("+UserColumn_1+" INT, "+UserColumn_2+" TEXT,"+UserColumn_3+" TEXT,"+UserColumn_4+" TEXT, "+UserColumn_5+" INT)";
        Log.i(TAG, "onCreate: "+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //null
    }

    //check if there is a user in app, if not then go login
    public boolean check4User(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UserTableName, null);
        Log.i(TAG, "check4User: Cursor count "+ cursor.getCount());
        if(cursor.getCount() != 1){
            return false; //there is no user
        } else return true; //yes there is a user in app
    }

    //delete all user and go login
    public void userLogout(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UserTableName, null);
        if(cursor.getCount() == 0){
            Log.i(TAG, "userLogout: Error there is no user in app, can't logout 4 sure");
        } else {
            long result = db.delete(UserTableName, null, null);
            Log.i(TAG, "userLogout: Complete delete "+ cursor.getCount()+" user(s)");
        }
    }

    public boolean userLogin(user user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserColumn_1, user.getId());
        contentValues.put(UserColumn_2, user.getUsername());
        contentValues.put(UserColumn_3, user.getUserLoginId());
        contentValues.put(UserColumn_4, user.getUserLoginPassword());
        contentValues.put(UserColumn_5, user.getPriv());
        long result = db.insert(UserTableName, null, contentValues);
        if(result == -1){
            Log.i(TAG, "userLogin: Error, There might be a error here");
            return false;
        } else {
            Log.i(TAG, "userLogin: User loged in Complete");
            return true;
        }
    }

    public user getUSer(){
        user defaultUser = new user(0,"shrimp","","",0);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UserTableName, null);
        while (cursor.moveToNext()) {
            user theUser = new user(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.valueOf(cursor.getString(4)));
            return theUser;
        }
        return defaultUser;
    }
}

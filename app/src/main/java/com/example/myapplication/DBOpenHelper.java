package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myAccount.db";
    private static final String DB_TABLE = "Account";
    private static final int DB_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_Amount = "amount";
    public static final String KEY_Category = "category";

    SQLiteDatabase db;

    private static final String DB_CREATE = "create table " +
            DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
            KEY_Amount + " text not null, " + KEY_Category + " text);";

    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("DB","DB created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        Log.d("DB","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        db.execSQL(DB_CREATE);
    }
    public boolean insertData(String amount, String category){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Amount,amount);
        contentValues.put(KEY_Category,category);
        Log.d("DEBUG","Amount:"+amount+" category: "+category);
        long result = db.insert(DB_TABLE,null,contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        db = this.getWritableDatabase();
        Cursor result = db.query(DB_TABLE,
                null,null,null,null,null,null);
        return result;
    }

}

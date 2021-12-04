package com.example.chatx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MsgDatabase extends SQLiteOpenHelper {
    Context context;

    public MsgDatabase(Context context){
        super(context, DBParams.DB_NAME, null, DBParams.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + DBParams.TABLE_NAME + "(" + DBParams.KEY_NUMBER + " TEXT, " +
                DBParams.KEY_MESSAGES + " TEXT" + ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMessage(String number, String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBParams.KEY_NUMBER, number);
        values.put(DBParams.KEY_MESSAGES, msg);
        db.insert(DBParams.TABLE_NAME,null, values);
        db.close();
    }

    public ArrayList<String> getMessage(String number){
        ArrayList<String> messages =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + DBParams.TABLE_NAME + " WHERE " + DBParams.KEY_NUMBER + "=\"" +  number + "\"";
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()) {
            do {
                String messages1 = cursor.getString(1) ;
                messages.add(messages1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messages;
    }

    public void delete(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBParams.TABLE_NAME, DBParams.KEY_NUMBER + "=?", new String[]{number});
    }

//    public  void deleteTable(){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + DBParams.TABLE_NAME);
//    }

}

package com.example.maxcion_home.jdmall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maxcion_home.jdmall.cons.DbContent;

import java.util.HashMap;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class UserDao {
    DbHelper mHelper;
    private final SQLiteDatabase db;

    public UserDao(Context context) {
        mHelper = new DbHelper(context);
        db = mHelper.getWritableDatabase();
    }

    public boolean saveUser(String name, String pwd) {

        ContentValues values = new ContentValues();
        values.put(DbContent._NAME, name);
        values.put(DbContent._PWD, pwd);
        long insert = db.insert(DbContent.TABLE_NAME, null, values);
        return insert != -1;
    }

    public void deleteUser(){
        db.delete(DbContent.TABLE_NAME,null,null);
    }

    public HashMap<String,String> getLastUser(){
        HashMap<String,String> map = new HashMap<>();
        Cursor cursor = db.query(DbContent.TABLE_NAME, new String[]{DbContent._NAME, DbContent._PWD},
                null, null, null, null, null);
        if (cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DbContent._NAME));
            String pwd = cursor.getString(cursor.getColumnIndex(DbContent._PWD));
            map.put("name",name);
            map.put("pwd",pwd);
        }
        return map;
    }


}

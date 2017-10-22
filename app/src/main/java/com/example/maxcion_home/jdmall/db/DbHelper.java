package com.example.maxcion_home.jdmall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.maxcion_home.jdmall.cons.DbContent;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, DbContent.DB_NAME, null, DbContent.DB_VERSION);
    }

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly()){
//            db.execSQL("pragma");
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +
                DbContent.TABLE_NAME + "(" +
                DbContent._ID + " integer primary key autoincrement," +
                DbContent._NAME + " text," +
                DbContent._PWD + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

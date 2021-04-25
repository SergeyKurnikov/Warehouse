package com.example.warehouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


/**
 * Класс для работы с базой данных, содержит методы создания, удаления таблиц
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class DBHelper extends SQLiteOpenHelper {

    /**Константы храняшие названия базы, таблиц и ее полей*/
    public  static final int DATABASE_VERSION = 1;
    public  static final String DATABASE_NAME = "completeDB";
    public  static final String TABLE_COMPLETE= "complete";
    public  static final String KEY_ID = "_id";
    public  static final String KEY_NAME = "name";
    public  static final String KEY_TYPE = "type";
    public  static final String KEY_NUMBER = "number";
    public  static final String KEY_PROVIDER = "provider";
    public  static final String KEY_DATE = "date";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *  Создание таблицы в БД при помощи метода execSQL()
     *  @param db - объект класса SQLiteDatabase для создания таблицы
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_COMPLETE + "(" + KEY_ID + " integer primary key," +
                KEY_NAME + " text, "+ KEY_TYPE + " text, " + KEY_NUMBER + " text, " + KEY_PROVIDER +
                " text, " + KEY_DATE + " text)");
    }

    /**
     *  Удаление старой таблицы и пеересоздание новой
     *  @param db - объект класса SQLiteDatabase для создания таблицы
     *  @param oldVersion - старая версия базы
     *  @param newVersion - новая версия базы
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COMPLETE);
        onCreate(db);
    }
}

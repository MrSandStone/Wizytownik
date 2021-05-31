package com.bielskiAdam.wizytownik.DataBaseModel;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;


public class DatabaseFactory extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "businesscard.db";
    private static final String TABLE_NAME = "businesscards";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME
                + "("
                + COLUMN_ID + " INTIGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE + " BLOB"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    ArrayList<BusinessCard> listBusinessCards() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BusinessCard> storeCards = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                String address = cursor.getString(3);
                String description = cursor.getString(4);
                byte[] image = cursor.getBlob(5);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return storeCards;
    }

    public void addCard(BusinessCard businessCard){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TITLE, businessCard.getTitle());
            cv.put(COLUMN_PHONE_NUMBER, businessCard.getPhoneNumber());
            cv.put(COLUMN_ADDRESS, businessCard.getAddress());
            cv.put(COLUMN_DESCRIPTION, businessCard.getDescription());
            cv.put(COLUMN_IMAGE, businessCard.getImage());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
    }

    public void updateCard(BusinessCard businessCard){
        try{
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TITLE, businessCard.getTitle());
            cv.put(COLUMN_PHONE_NUMBER, businessCard.getPhoneNumber());
            cv.put(COLUMN_ADDRESS, businessCard.getAddress());
            cv.put(COLUMN_DESCRIPTION, businessCard.getDescription());
            cv.put(COLUMN_IMAGE, businessCard.getImage());
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_NAME, cv, COLUMN_ID + " =?", new String[]{ String.valueOf(businessCard.getId())});
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteCard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " =?", new String[]{String.valueOf(id)});
    }
}

package com.bielskiAdam.wizytownik.DataModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = BusinessCard.class,
        version = 1,
        exportSchema = false
)
public abstract class BusinessCardDatabase extends RoomDatabase{
    private static BusinessCardDatabase businessCardDB = null;

    public abstract DataBaseDAO databaseDao();

    public static synchronized BusinessCardDatabase getDBInstance(Context context){
        if(businessCardDB == null){
            businessCardDB = Room.databaseBuilder(
                    context.getApplicationContext(),
                    BusinessCardDatabase.class,
                    "businessCard19b2"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return businessCardDB;
    }
}

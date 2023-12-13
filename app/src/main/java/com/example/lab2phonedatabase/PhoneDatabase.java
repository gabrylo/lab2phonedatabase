package com.example.lab2phonedatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneDatabase extends RoomDatabase {

    public abstract PhoneDao phoneDao();

    private static volatile PhoneDatabase INSTANCE;

    public static PhoneDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhoneDatabase.class, "phone_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


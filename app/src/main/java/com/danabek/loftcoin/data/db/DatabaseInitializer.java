package com.danabek.loftcoin.data.db;

import android.content.Context;

import com.danabek.loftcoin.data.db.room.AppDatabase;
import com.danabek.loftcoin.data.db.room.DatabaseImplRoom;

import androidx.room.Room;

public class DatabaseInitializer {

    public Database init(Context context) {
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "loftcoin.db")
                .fallbackToDestructiveMigration()
                .build();

        return new DatabaseImplRoom(appDatabase);
    }
}

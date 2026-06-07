package com.luxuryethiopia2.core.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.luxuryethiopia2.core.domain.model.UserAccount;

@Database(entities = {UserAccount.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract UserAccountDao userAccountDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "luxury_ethiopia_core_db"
                )
                .fallbackToDestructiveMigration()
                .build();
        }
        return instance;
    }
}

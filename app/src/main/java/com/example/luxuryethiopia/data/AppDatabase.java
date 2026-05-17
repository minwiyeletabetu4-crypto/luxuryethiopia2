package com.example.luxuryethiopia.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.luxuryethiopia.data.dao.BookingDao;
import com.example.luxuryethiopia.data.dao.DestinationDao;
import com.example.luxuryethiopia.data.dao.UserDao;
import com.example.luxuryethiopia.data.entity.Booking;
import com.example.luxuryethiopia.data.entity.Destination;
import com.example.luxuryethiopia.data.entity.User;

@Database(entities = {User.class, Destination.class, Booking.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract DestinationDao destinationDao();
    public abstract BookingDao bookingDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "luxury_ethiopia_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

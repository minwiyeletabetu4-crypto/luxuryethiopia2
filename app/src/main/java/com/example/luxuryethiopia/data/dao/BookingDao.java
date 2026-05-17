package com.example.luxuryethiopia.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.luxuryethiopia.data.entity.Booking;

import java.util.List;

@Dao
public interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBooking(Booking booking);

    @Query("SELECT * FROM bookings WHERE fk_user_id = :userId")
    List<Booking> getBookingsForUser(int userId);

    @Query("SELECT * FROM bookings")
    List<Booking> getAllBookings();
}

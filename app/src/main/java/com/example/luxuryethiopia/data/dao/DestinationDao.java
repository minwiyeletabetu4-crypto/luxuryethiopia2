package com.example.luxuryethiopia.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.luxuryethiopia.data.entity.Destination;

import java.util.List;

@Dao
public interface DestinationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDestination(Destination destination);

    @Delete
    void deleteDestination(Destination destination);

    @Query("SELECT * FROM destinations")
    List<Destination> getAllDestinations();
}


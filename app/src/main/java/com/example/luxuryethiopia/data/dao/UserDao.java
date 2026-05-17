package com.example.luxuryethiopia.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.luxuryethiopia.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE user_email = :email LIMIT 1")
    User findUserByEmail(String email);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
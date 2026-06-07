package com.luxuryethiopia2.core.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.luxuryethiopia2.core.domain.model.UserAccount;

@Dao
public interface UserAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long registerUser(UserAccount user);

    @Query("SELECT * FROM user_accounts WHERE (username = :username OR email = :username) AND password_hash = :passwordHash LIMIT 1")
    UserAccount findUserByCredentials(String username, String passwordHash);

    @Query("SELECT * FROM user_accounts WHERE id = :userId LIMIT 1")
    UserAccount findUserById(int userId);

    @Query("UPDATE user_accounts SET is_verified = :isVerified WHERE id = :userId")
    void updateVerificationStatus(int userId, boolean isVerified);
}

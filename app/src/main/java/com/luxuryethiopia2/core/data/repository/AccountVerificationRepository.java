package com.luxuryethiopia2.core.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.luxuryethiopia2.core.data.local.AppDatabase;
import com.luxuryethiopia2.core.data.local.UserAccountDao;
import com.luxuryethiopia2.core.domain.model.UserAccount;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountVerificationRepository {

    private final UserAccountDao userAccountDao;
    private final ExecutorService executor;

    public AccountVerificationRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.userAccountDao = database.userAccountDao();
        this.executor = Executors.newFixedThreadPool(2);
    }

    public LiveData<Long> registerUser(String username, String email, String password) {
        MutableLiveData<Long> result = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                String hash = sha256(password);
                UserAccount user = new UserAccount(username, email, hash);
                long id = userAccountDao.registerUser(user);
                user.setId((int) id);
                result.postValue(id);
            } catch (Exception e) {
                result.postValue(-1L);
            }
        });
        return result;
    }

    public LiveData<UserAccount> authenticateUser(String username, String password) {
        MutableLiveData<UserAccount> result = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                String hash = sha256(password);
                UserAccount user = userAccountDao.findUserByCredentials(username, hash);
                result.postValue(user);
            } catch (Exception e) {
                result.postValue(null);
            }
        });
        return result;
    }

    public LiveData<Boolean> checkVerificationStatus(int userId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                UserAccount user = userAccountDao.findUserById(userId);
                result.postValue(user != null && user.isVerified());
            } catch (Exception e) {
                result.postValue(false);
            }
        });
        return result;
    }

    public void verifyUser(int userId) {
        executor.execute(() -> userAccountDao.updateVerificationStatus(userId, true));
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}

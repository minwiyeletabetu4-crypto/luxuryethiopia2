package com.example.luxuryethiopia.repository;

import android.app.Application;

import com.example.luxuryethiopia.data.AppDatabase;
import com.example.luxuryethiopia.data.dao.UserDao;
import com.example.luxuryethiopia.data.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataRepository {

    private final UserDao userDao;
    private final ExecutorService executorService;

    public DataRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
        executorService = Executors.newFixedThreadPool(4);
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insertUser(user));  // ← fixed: was insert()
    }
}
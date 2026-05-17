package com.example.luxuryethiopia.VewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.luxuryethiopia.data.entity.User;
import com.example.luxuryethiopia.repository.DataRepository;

public class UserViewModel extends AndroidViewModel {

    private final DataRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public void registerUser(User user) {
        repository.insertUser(user);
    }
}
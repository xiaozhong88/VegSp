package com.atinytot.vegsp_v_1.ui.setting;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.atinytot.vegsp_v_1.model.repository.Result;
import com.atinytot.vegsp_v_1.model.repository.UserRepository;
import com.atinytot.vegsp_v_1.room.entity.User;

public class SettingViewModel extends AndroidViewModel {

    private UserRepository repository;
    private MutableLiveData<User> user;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        user = new MutableLiveData<>();
    }

    public void updateUsers(User users) {
        repository.updateUsers(users);
    }

    public LiveData<User> getUserLive() {
        return user;
    }

    public void findUser(String username, String password) {
        repository.findUser(username, password, result -> {
            if (result instanceof Result.Success) {
                user.postValue(((Result.Success<User>) result).getData());
            } else {
                Log.i("pass:", "错误");
            }
        });
    }
}

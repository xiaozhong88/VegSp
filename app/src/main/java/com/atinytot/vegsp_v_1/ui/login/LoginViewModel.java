package com.atinytot.vegsp_v_1.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.atinytot.vegsp_v_1.models.repository.Result;
import com.atinytot.vegsp_v_1.models.repository.UserRepository;
import com.atinytot.vegsp_v_1.room.entity.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository repository;
    private MutableLiveData<User> userLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userLiveData = new MutableLiveData<>();
    }

    public void insertUsers(User... users) {
        repository.insertUsers(users);
    }

    public void deleteUsers(User... users) {
        repository.deleteUsers(users);
    }

    public void deleteAllUsers() {
        repository.deleteAllUsers();
    }

    public void updateUsers(User... users) {
        repository.updateUsers(users);
    }

    public LiveData<List<User>> getAllUsersLive() {
        return repository.getAllUsersLive();
    }

    public LiveData<User> getUserLive() {
        return userLiveData;
    }

    public void findUser(String username, String password) {
        repository.findUser(username, password, result -> {
            if (result instanceof Result.Success) {
                userLiveData.postValue(((Result.Success<User>) result).getData());
            } else {
                Log.i("pass:", "错误");
                userLiveData.postValue(null);
            }
        });
//        repository.findUser(username, password, new OnResultCallback<Result<User>>() {
//            @Override
//            public void onResult(Result<User> result) {
//
//            }
//        });
    }
}

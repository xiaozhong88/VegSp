package com.atinytot.vegsp_v_1.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.atinytot.vegsp_v_1.model.repository.Result;
import com.atinytot.vegsp_v_1.model.repository.UserRepository;
import com.atinytot.vegsp_v_1.room.UserDataBase;
import com.atinytot.vegsp_v_1.room.dao.UserDao;
import com.atinytot.vegsp_v_1.room.entity.User;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository repository;
    private MutableLiveData<User> data;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        data = new MutableLiveData<>();
    }

    public LiveData<User> getData() {
        return data;
    }

    public void registerUser(String username, String password) {
        repository.findUser(username, password, result -> {
            if (result instanceof Result.Success) {
                data.postValue(result.getData());
            } else if (result instanceof Result.Failure) {
                repository.insertUsers(new User(username, password));
            }
        });

    }
}

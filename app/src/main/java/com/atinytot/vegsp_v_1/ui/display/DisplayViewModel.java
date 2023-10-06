package com.atinytot.vegsp_v_1.ui.display;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.models.repository.DisplayRepository;
import com.atinytot.vegsp_v_1.models.repository.DisplayRepositoryCallback;

public class DisplayViewModel extends AndroidViewModel implements DisplayRepositoryCallback {

    private static DisplayRepository repository;
    private MutableLiveData<Environment> data;

    public DisplayViewModel(@NonNull Application application) {
        super(application);
        repository = new DisplayRepository(application, this);
        data = new MutableLiveData<>();
    }

    public MutableLiveData<Environment> getData() {
//        data.setValue(repository.getData());
        return data;
    }

    public boolean connect() {
        return repository.connect();
    }

    public void disconnect() {
        repository.disconnect();
    }

    @Override
    public void onDataUpdated(@NonNull Environment environment) {
        data.setValue(environment);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.clear();
    }
}
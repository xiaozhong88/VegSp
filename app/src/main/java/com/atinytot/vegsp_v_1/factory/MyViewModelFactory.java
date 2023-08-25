package com.atinytot.vegsp_v_1.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.atinytot.vegsp_v_1.ui.display.DisplayViewModel;
import com.atinytot.vegsp_v_1.ui.ranging.RangingViewModel;
import com.atinytot.vegsp_v_1.ui.video.VideoViewModel;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application application;
    private SavedStateHandle savedStateHandle;

    public MyViewModelFactory(Application application, SavedStateHandle savedStateHandle) {
        this.application = application;
        this.savedStateHandle = savedStateHandle;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DisplayViewModel.class) {
            return (T) new DisplayViewModel(application);
        } else if (modelClass == VideoViewModel.class) {
            return (T) new VideoViewModel(application);
        } else if (modelClass == RangingViewModel.class) {
            return (T) new RangingViewModel(application);
        }
        return super.create(modelClass);
    }
}
package com.atinytot.vegsp_v_1.ui.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.atinytot.vegsp_v_1.models.repository.VideoRepository;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoViewModel extends AndroidViewModel {

//    private SavedStateHandle handle;
//    private MqttManager manager;
    boolean state;

    private static VideoRepository repository;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        repository = new VideoRepository(application, null);
    }

//    public VideoViewModel(@NonNull Application application, SavedStateHandle handle) {
//        super(application);
//        this.handle = handle;
//
//        String productKey = handle.get("productKey");
//        String deviceName = handle.get("deviceName");
//        String deviceSecret = handle.get("deviceSecret");
//
//        manager = MqttManagerFactory.createMqttManager("test", application, productKey, deviceName, deviceSecret);
//    }

    public boolean connect() {
        state = repository.connect();
        return state;
    }

    public void disconnect() {
        repository.disconnect();
    }

    public void setStatus(int status) {
        JSONObject push = new JSONObject();
        try {
            push.put("deviceName", "APPtest");
            push.put("left_right", status);
            if (state) {
                repository.putData(push.toString());
            }
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.clear();
    }
}

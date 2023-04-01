package com.atinytot.vegsp_v_1.ui.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;

import com.atinytot.vegsp_v_1.factory.MqttManagerFactory;
import com.atinytot.vegsp_v_1.manage.MqttManager;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoViewModel extends AndroidViewModel {

    private SavedStateHandle handle;

    private MqttManager manager;
    boolean state;
    /* 自动Topic, 用于发布消息 */
    final private String PUB_TOPIC = "/gta2ifaeh8F/test/user/controlCommand";

    public VideoViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;

        String productKey = handle.get("productKey");
        String deviceName = handle.get("deviceName");
        String deviceSecret = handle.get("deviceSecret");

        manager = MqttManagerFactory.createMqttManager("test", application, productKey, deviceName, deviceSecret);
    }

    public void connect() {
        state = manager.connectServer();
    }

    public void disconnect() {
        MqttManagerFactory.removeMqttManager("test");
    }

    public void setStatus(int status) {
        JSONObject push = new JSONObject();
        try {
            push.put("deviceName", "APPtest");
            push.put("left_right", status);
            if (state) {
                manager.pubTopic(PUB_TOPIC, push.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

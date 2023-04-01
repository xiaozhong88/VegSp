package com.atinytot.vegsp_v_1.ui.setting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.atinytot.vegsp_v_1.domain.Distance;
import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.factory.MqttManagerFactory;
import com.atinytot.vegsp_v_1.manage.MqttManager;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingViewModel extends AndroidViewModel implements MqttManager.OnMessageReceivedListener {

    private SavedStateHandle handle;
    private final MutableLiveData<Distance> data;

    private MqttManager manager;
    boolean state;
    /* 自定义Topic, 用于接受消息 */
    final private String SUB_TOPIC = "/gta2ifaeh8F/test_recv/user/receiveParameters";

    public SettingViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;

        String productKey = handle.get("productKey");
        String deviceName = handle.get("deviceName");
        String deviceSecret = handle.get("deviceSecret");

        data = new MutableLiveData<>();
        manager = MqttManagerFactory.createMqttManager("test_recv", application, productKey, deviceName, deviceSecret);
        // TODO 传入的this代表的是当前类的对象，而该类实现了OnMessageReceivedListener接口，因此可以将该对象作为OnMessageReceivedListener的实现对象进行赋值
        manager.setOnMessageReceivedListener(this);
    }

    public void connect() {
        state = manager.connectServer();
        if (state) {
            manager.subTopic(SUB_TOPIC, 0);
        }
    }

    public void disconnect() {
        MqttManagerFactory.removeMqttManager("test_recv");
    }

    public MutableLiveData<Distance> getData() {
        return data;
    }

    @Override
    public void onMessageReceived(MqttMessage message) {
        try {
            String sub_data = new String(message.getPayload());
            JSONObject resp = new JSONObject(sub_data);
            Distance distance = new Distance();
            distance.setDistance1("15");
            distance.setDistance2("15");
            distance.setDistance3("22");
            distance.setDistance4("55");
            data.setValue(distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        manager.removeOnMessageReceivedListener(); // 移除回调接口
    }

}
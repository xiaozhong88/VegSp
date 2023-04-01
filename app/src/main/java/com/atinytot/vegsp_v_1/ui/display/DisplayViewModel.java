package com.atinytot.vegsp_v_1.ui.display;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.factory.MqttManagerFactory;
import com.atinytot.vegsp_v_1.manage.MqttManager;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayViewModel extends AndroidViewModel implements MqttManager.OnMessageReceivedListener{

    private SavedStateHandle handle;
    private final MutableLiveData<Environment> data;
    private String key = getApplication().getResources().getString(R.string.data_key);
    private String shpName = getApplication().getResources().getString(R.string.shp_name);

    private MqttManager manager;
    private MqttMessage message;
    private boolean state = false;
    /* 自定义Topic, 用于接受消息 */
    final private String SUB_TOPIC = "/gta2ifaeh8F/test_recv/user/receiveParameters";

    public DisplayViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        String productKey = handle.get("productKey");
        String deviceName = handle.get("deviceName");
        String deviceSecret = handle.get("deviceSecret");
//        if (!handle.contains(key)) {
//            load();
//        }

        data = new MutableLiveData<Environment>();
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

    private LiveData<Integer> getNumber() {
        return handle.getLiveData(key);
    }

    private void load() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        int x = shp.getInt(key, 0);
        handle.set(key, x);
    }

    private void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(key, getNumber().getValue());
        editor.apply();
    }

    private void add(int x) {
        handle.set(key, getNumber().getValue() + x);
//        save();
    }

    public MutableLiveData<Environment> getDisplayData() {

        return data;
    }

    public void setData(Environment environment) {
        this.data.setValue(environment);
    }

    public MutableLiveData<Environment> getData() {

        return data;
    }

    @Override
    public void onMessageReceived(MqttMessage message) {
        this.message = message;
        try {
            String sub_data = new String(message.getPayload());
            JSONObject resp = new JSONObject(sub_data);
            Environment environment = new Environment();
            environment.setSoil_humidity(resp.getString("EnvironmentHumidity"));
            environment.setSoil_temp(resp.getString("EnvironmentTemperature"));
            environment.setAir_humidity("22");
            environment.setAir_temp("23");
            environment.setCO2("24");
            environment.setHv("25");
            data.setValue(environment);
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
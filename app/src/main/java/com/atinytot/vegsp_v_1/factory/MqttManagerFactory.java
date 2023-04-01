package com.atinytot.vegsp_v_1.factory;

import android.content.Context;

import com.atinytot.vegsp_v_1.manage.MqttManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MqttManagerFactory {

    // TODO 使用缓存等机制来提升创建效率
    private static final Map<String, MqttManager> managers = new HashMap<>();

    public static MqttManager createMqttManager(String clientId, Context context, String productKey, String deviceName, String deviceSecret) {
        MqttManager manager = managers.get(clientId);
        if (manager == null) {
            manager = new MqttManager(context, productKey, deviceName, deviceSecret);
            managers.put(clientId, manager);
        }
        return manager;
    }

    public static void removeMqttManager(String clientId) {
        MqttManager mqttManager = managers.remove(clientId);
        if (mqttManager != null) {
            mqttManager.disconnect();
        }
    }
}

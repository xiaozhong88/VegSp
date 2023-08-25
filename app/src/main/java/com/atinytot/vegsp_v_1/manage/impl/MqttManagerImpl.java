package com.atinytot.vegsp_v_1.manage.impl;

import com.atinytot.vegsp_v_1.domain.Distance;
import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.manage.MqttManager;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class MqttManagerImpl implements MqttManager.OnMessageReceivedListener {

    @Override
    public void onMessageReceived(MqttMessage message) {

    }

//    @Override
//    public void displayOnMessageReceived(MqttMessage message) throws JSONException {
//        Environment environment = new Environment();
//        String sub_data = new String(message.getPayload());
//        JSONObject json = new JSONObject(sub_data);
//
//        environment.setSoil_temp(json.getString("soilTemperature"));
//        environment.setSoil_humidity(json.getString("soilHumidity"));
//        environment.setAir_temp(json.getString("airTemp"));
//        environment.setAir_humidity(json.getString("airHumidity"));
//        environment.setHv(json.getString("Env_lux"));
//        environment.setCO2(json.getString("CO2Value"));
//    }
//
//    @Override
//    public void videoOnMessageReceived(MqttMessage message) throws JSONException {
//
//    }
//
//    @Override
//    public void settingOnMessageReceived(MqttMessage message) throws JSONException {
//        Distance distance = new Distance();
//        String sub_data = new String(message.getPayload());
//        JSONObject json = new JSONObject(sub_data);
//
//        distance.setDistance1(json.getString("soilTemperature"));
//        distance.setDistance2(json.getString("soilHumidity"));
//        distance.setDistance3(json.getString("Env_lux"));
//        distance.setDistance4(json.getString("CO2Value"));
//    }
}

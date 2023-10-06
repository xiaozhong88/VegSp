package com.atinytot.vegsp_v_1.models.repository

import android.app.Application
import android.util.Log
import com.atinytot.vegsp_v_1.domain.Environment
import com.atinytot.vegsp_v_1.factory.MqttManagerFactory
import com.atinytot.vegsp_v_1.manage.MqttManager
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject

class DisplayRepository(application: Application, private val callback: DisplayRepositoryCallback) :
    MqttManager.OnMessageReceivedListener {

    private val manager: MqttManager
    private var data: Environment

    companion object {
        private const val PRODUCTKEY: String = "gta2ifaeh8F"
        private const val DEVICENAME = "test_recv"
        private const val DEVICESECRET = "8b5d43589328b10b3cb704ad0a0323d4"

        /* 自定义Topic, 用于接受消息 */
        private const val SUB_TOPIC = "/gta2ifaeh8F/test_recv/user/receiveParameters"
    }

    init {
        manager = MqttManagerFactory.createMqttManager(
            "test_recv",
            application,
            PRODUCTKEY,
            DEVICENAME,
            DEVICESECRET
        )
        manager.setOnMessageReceivedListener(this)

        data = Environment()
    }

    private fun isConnect(): Boolean {
        return manager.isConnected
    }

    fun connect(): Boolean {
        if (!isConnect() && MqttManagerFactory.isExistedMqttManager("test_recv")) {
            val state = manager.connectServer();
            if (state) {
                manager.subTopic(SUB_TOPIC, 0)
                return true
            }
            return false
        } else {
            return true
        }
    }

    fun disconnect() {
        manager.disconnect()
        MqttManagerFactory.removeMqttManager("test_recv")
    }

    fun getData(): Environment {

        return data
    }

    override fun DisplayOnMessageReceived(message: MqttMessage?) {
//        super.displayOnMessageReceived(message)

        try {
            manager.mqttMessage?.let {
                val sub_data = String(it.payload)
                Log.i(
                    "display--->mqtt",
                    "收到消息： " + String(it.payload) + "\tToString:" + it.toString()
                )
                val resp = JSONObject(sub_data)
                data.soil_temp = resp.getString("soilTemperature")
                data.soil_humidity = resp.getString("soilHumidity")
                data.air_temp = resp.getString("airTemp")
                data.air_humidity = resp.getString("airHumidity")
                data.hv = resp.getString("Env_lux")
                data.cO2 = resp.getString("CO2Value")
                callback.onDataUpdated(data)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun clear() = manager.removeOnMessageReceivedListener()
}

interface DisplayRepositoryCallback {
    fun onDataUpdated(environment: Environment)
}
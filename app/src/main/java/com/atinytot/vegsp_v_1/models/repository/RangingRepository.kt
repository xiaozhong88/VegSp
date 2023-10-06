package com.atinytot.vegsp_v_1.models.repository

import android.app.Application
import android.util.Log
import com.atinytot.vegsp_v_1.domain.Distance
import com.atinytot.vegsp_v_1.factory.MqttManagerFactory
import com.atinytot.vegsp_v_1.manage.MqttManager
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONException
import org.json.JSONObject

class RangingRepository(application: Application, private val callback: RangingRepositoryCallback) :
    MqttManager.OnMessageReceivedListener {

    private val manager: MqttManager
    private var data: Distance

    companion object {
        private const val PRODUCTKEY = "gta2ifaeh8F"
        private const val DEVICENAME = "test_recv"
        private const val DEVICESECRET = "8b5d43589328b10b3cb704ad0a0323d4"

        /* 自定义Topic, 用于接受消息 */
        var SUB_TOPIC = "/gta2ifaeh8F/test_recv/user/receiveParameters"
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

        data = Distance()
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

    fun getData(): Distance {

        return data
    }

    override fun RangingOnMessageReceived(message: MqttMessage?) {
        try {
            manager.mqttMessage?.let {
                val sub_data = String(it.payload)
                Log.i(
                    "setting--->mqtt",
                    "收到消息： " + String(message!!.payload) + "\tToString:" + message.toString()
                )
                val resp = JSONObject(sub_data)
                data.distance1 = resp.getString("soilTemperature")
                data.distance2 = resp.getString("soilHumidity")
                data.distance3 = resp.getString("airTemp")
                data.distance4 = resp.getString("airHumidity")
                callback.onDataUpdated(data)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun clear() = manager.removeOnMessageReceivedListener()
}

interface RangingRepositoryCallback {
    fun onDataUpdated(distance: Distance)
}
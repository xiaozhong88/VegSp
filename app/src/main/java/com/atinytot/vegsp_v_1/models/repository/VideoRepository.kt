package com.atinytot.vegsp_v_1.models.repository

import android.app.Application
import com.atinytot.vegsp_v_1.domain.Environment
import com.atinytot.vegsp_v_1.factory.MqttManagerFactory
import com.atinytot.vegsp_v_1.manage.MqttManager
import org.eclipse.paho.client.mqttv3.MqttMessage

class VideoRepository(application: Application, private val callback: VideoRepositoryCallback?) :
    MqttManager.OnMessageReceivedListener {

    private val manager: MqttManager
//    private var data: Environment

    companion object {
        private const val PRODUCTKEY = "gta2ifaeh8F"
        private const val DEVICENAME = "test"
        private const val DEVICESECRET = "23e932b5934dfa4813b48ddb8de207e7"

        /* 自动Topic, 用于发布消息 */
        private const val PUB_TOPIC = "/gta2ifaeh8F/test/user/controlCommand"
    }

    init {
        manager = MqttManagerFactory.createMqttManager(
            "test",
            application,
            PRODUCTKEY,
            DEVICENAME,
            DEVICESECRET
        )
        manager.setOnMessageReceivedListener(this)

//        data = Environment()
    }

    private fun isConnect(): Boolean {
        return manager.isConnected
    }

    fun connect(): Boolean {
        if (!isConnect() && MqttManagerFactory.isExistedMqttManager("test")) {
            val state = manager.connectServer();
            if (state) {
                return true
            }
            return false
        } else {
            return true
        }
    }

    fun disconnect() {
        manager.disconnect()
        MqttManagerFactory.removeMqttManager("test")
    }

    //    fun getData(): Environment {
//
//        return data
//    }
    fun putData(value: String) {
        manager.pubTopic(PUB_TOPIC, value)
    }

    override fun VideoOnMessageReceived(message: MqttMessage?) {
//        super.displayOnMessageReceived(message)

//        try {
//            manager.mqttMessage?.let {
//                val sub_data = String(it.payload)
//                Log.i(
//                    "display--->mqtt",
//                    "收到消息： " + String(it.payload) + "\tToString:" + it.toString()
//                )
//                val resp = JSONObject(sub_data)
//                data.soil_temp = resp.getString("soilTemperature")
//                data.soil_humidity = resp.getString("soilHumidity")
//                data.air_temp = resp.getString("airTemp")
//                data.air_humidity = resp.getString("airHumidity")
//                data.hv = resp.getString("Env_lux")
//                data.cO2 = resp.getString("CO2Value")
//                callback?.onDataUpdated(data)
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
    }

    fun clear() = manager.removeOnMessageReceivedListener()
}

interface VideoRepositoryCallback {
    fun onDataUpdated(environment: Environment)
}
package com.atinytot.vegsp_v_1.manage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.atinytot.vegsp_v_1.model.repository.DisplayRepository;
import com.atinytot.vegsp_v_1.model.repository.RangingRepository;
import com.atinytot.vegsp_v_1.model.repository.VideoRepository;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MqttManager {

    // TODO MQTT相关配置
    // TODO 服务器地址（协议+地址+端口号）
    private final String region = "cn-shanghai";
    private static String username;
    private static String password;
    private static String clientId;
    private String productKey;
    private String deviceName;
    private String deviceSecret;
    private final String HOST = "tcp://" + productKey + ".iot-as-mqtt." + region + ".aliyuncs.com:443";

    //服务质量,0最多一次，1最少一次，2只一次
    private final static int QOS = 0;

    private Context mContext;
    private static MqttManager mqttManager;
    private MqttAndroidClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    private MqttMessage mqttMessage;
    private OnMessageReceivedListener listener;
    private OnMessageReceivedListener displayListener;
    private OnMessageReceivedListener videoListener;
    private OnMessageReceivedListener settingListener;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientId() {
        return clientId;
    }

    public MqttMessage getMqttMessage() {
        return mqttMessage;
    }

    private MqttManager() {
    }

    /**
     * TODO 构造函数
     *
     * @param mContext 上下文
     */
    public MqttManager(Context mContext, String productKey, String deviceName, String deviceSecret) {
        this.mContext = mContext;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        getMqttOption();
        initMqtt();
    }

//    /**
//     * TODO 单例模式
//     * @param context 上下文
//     * @return MqttManager对象
//     */
//    public static synchronized MqttManager getInstance(Context context, String productKey, String deviceName, String deviceSecret) {
//        if (mqttManager == null) {
//            mqttManager = new MqttManager(context, productKey, deviceName, deviceSecret);
//        }
//        return mqttManager;
//    }

    /**
     * TODO 获取MQTT连接信息
     */
    private void getMqttOption() {

        try {
            if (productKey != null && deviceName != null && deviceSecret != null) {
                String timestamp = Long.toString(System.currentTimeMillis());

                // clientId
                clientId = productKey + "." + deviceName + "|timestamp=" + timestamp +
                        ",_v=paho-android-1.0.0,securemode=2,signmethod=hmacsha256|";

                // userName
                username = deviceName + "&" + productKey;

                // password
                String macSrc = "clientId" + productKey + "." + deviceName + "deviceName" +
                        deviceName + "productKey" + productKey + "timestamp" + timestamp;
                String algorithm = "HmacSHA256";
                Mac mac = Mac.getInstance(algorithm);
                SecretKeySpec secretKeySpec = new SecretKeySpec(deviceSecret.getBytes(), algorithm);
                mac.init(secretKeySpec);
                byte[] macRes = mac.doFinal(macSrc.getBytes());
                password = String.format("%064x", new BigInteger(1, macRes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO 初始化
     */
    private void initMqtt() {

        // TODO 创建Mqtt客户端
        mqttClient = new MqttAndroidClient(mContext, HOST, clientId);
        mqttClient.setCallback(mqttCallback);

        // mqtt连接选项
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);                // TODO 清除缓存
        mqttConnectOptions.setConnectionTimeout(30);             // TODO 设置超时时间
        mqttConnectOptions.setKeepAliveInterval(60);             // TODO 设置心跳包发送间隔
        mqttConnectOptions.setUserName(username);                // TODO 清除缓存
        mqttConnectOptions.setPassword(password.toCharArray());  // TODO 清除缓存
    }

    /**
     * TODO 回调消息监听接口
     */
    public interface OnMessageReceivedListener {
        default void onMessageReceived(MqttMessage message) {

        }

        default void DisplayOnMessageReceived(MqttMessage message) throws JSONException {

        }

        default void VideoOnMessageReceived(MqttMessage message) throws JSONException {

        }

        default void RangingOnMessageReceived(MqttMessage message) throws JSONException {

        }
    }

    /**
     * TODO 设置监听器
     *
     * @param listener 监听器
     */
    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
//        this.listener = listener;
        if (listener instanceof DisplayRepository) {
            displayListener = listener;
        } else if (listener instanceof VideoRepository) {
            videoListener = listener;
        } else if (listener instanceof RangingRepository) {
            settingListener = listener;
        }
    }

    /**
     * TODO 移除监听器
     */
    public void removeOnMessageReceivedListener() {
        this.listener = null;
    }

    /**
     * TODO 订阅主题的回调
     */
    private final MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void messageArrived(String s, MqttMessage message) throws Exception {
             Log.i("--->mqtt", "收到消息： " + new String(message.getPayload()) + "\tToString:" + message.toString());
            mqttMessage = message;

//            if (displayListener != null) {
//                displayListener.displayOnMessageReceived(message);
//            } else if (videoListener != null) {
//                videoListener.videoOnMessageReceived(message);
//            } else if (settingListener != null) {
//                settingListener.settingOnMessageReceived(message);
//            }

            if (displayListener != null) {
                displayListener.DisplayOnMessageReceived(message);
            }
            if (videoListener != null) {
                videoListener.VideoOnMessageReceived(message);
            }
            if (settingListener != null) {
                settingListener.RangingOnMessageReceived(message);
            }
//            if (listener != null) {
//                Log.i("--->mqtt", "收到消息： " + new String(message.getPayload()) + "\tToString:" + message.toString());
//                listener.onMessageReceived(message);
//            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            Log.i("--->mqtt", "deliveryComplete");
        }
    };

    /**
     * TODO MQTT是否连接成功的监听
     */
    private final IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken iMqttToken) {
            Log.i("--->mqtt", "连接成功 ");
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
//            mqttClient.subscribe();
        }

        @Override
        public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
            throwable.printStackTrace();
        }
    };

    /**
     * TODO 建立mqtt连接，连接MQTT服务器
     *
     * @return 连接结果
     */
    public boolean connectServer() {

        try {
            if ((mqttClient != null) && (!mqttClient.isConnected())) {
                mqttClient.connect(mqttConnectOptions, null, iMqttActionListener);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * TODO 订阅主题
     *
     * @param topic 主题
     * @param qos   服务质量
     */
    public void subTopic(String topic, int qos) {

        try {
            if ((mqttClient != null) && (mqttClient.isConnected())) {
                mqttClient.subscribe(topic, qos);
                Toast.makeText(mContext, "订阅成功", Toast.LENGTH_SHORT).show();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /**
     * TODO 发布主题
     *
     * @param topic   主题
     * @param payLoad 发布信息
     */
    public void pubTopic(String topic, String payLoad) {
        try {
            if ((mqttClient != null) && (mqttClient.isConnected())) {
                // TODO 参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
                Toast.makeText(mContext, "发布成功", Toast.LENGTH_LONG).show();
                mqttClient.publish(topic, payLoad.getBytes(), QOS, false);
            } else {
                Log.i("--->mqtt", "mqttAndroidClient is Null or is not connected");
            }
        } catch (Exception e) {
            Log.i("--->mqtt", "publish MqttException:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * TODO 返回连接状态
     *
     * @return true连接 false为未连接
     */
    public boolean isConnected() {
        return (mqttClient != null) && (mqttClient.isConnected());
    }

    /**
     * TODO 断开MQTT连接
     */
    public void disconnect() {
        try {
            if (mqttClient != null) {
                mqttClient.unregisterResources();
                mqttClient.disconnect();
                mqttClient = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

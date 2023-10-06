package com.atinytot.vegsp_v_1.ui.ranging;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.atinytot.vegsp_v_1.domain.Distance;
import com.atinytot.vegsp_v_1.models.repository.RangingRepository;
import com.atinytot.vegsp_v_1.models.repository.RangingRepositoryCallback;

public class RangingViewModel extends AndroidViewModel implements RangingRepositoryCallback {

    private static RangingRepository repository;
    private static MutableLiveData<Distance> data;

    public RangingViewModel(@NonNull Application application) {
        super(application);
        repository = new RangingRepository(application, this);
        data = new MutableLiveData<>();
    }

    public MutableLiveData<Distance> getData() {
//        data.setValue(repository.getData());
        return data;
    }

    public boolean connect() {
        return repository.connect();
    }

    public void disconnect() {
        repository.disconnect();
    }

    @Override
    public void onDataUpdated(@NonNull Distance distance) {
        data.setValue(distance);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.clear();
    }
}
//public class RangingViewModel extends AndroidViewModel implements MqttManager.OnMessageReceivedListener {
//
//    private SavedStateHandle handle;
//    private final MutableLiveData<Distance> data;
//
//    private MqttManager manager;
//    boolean state;
//
//    public RangingViewModel(@NonNull Application application, SavedStateHandle handle) {
//        super(application);
//        this.handle = handle;
//
//        String productKey = handle.get("productKey");
//        String deviceName = handle.get("deviceName");
//        String deviceSecret = handle.get("deviceSecret");
//
//        data = new MutableLiveData<>();
//        manager = MqttManagerFactory.createMqttManager("test_recv", application, productKey, deviceName, deviceSecret);
//        // TODO 传入的this代表的是当前类的对象，而该类实现了OnMessageReceivedListener接口，因此可以将该对象作为OnMessageReceivedListener的实现对象进行赋值
//        manager.setOnMessageReceivedListener(this);
//    }
//
//    public void connect() {
//        if (MqttManagerFactory.isExistedMqttManager("test_recv")) {
//            state = manager.connectServer();
//            if (state) {
//                /* 自定义Topic, 用于接受消息 */
//                String SUB_TOPIC = "/gta2ifaeh8F/test_recv/user/receiveParameters";
//                manager.subTopic(SUB_TOPIC, 0);
//            }
//        }
//    }
//
//    public void disconnect() {
//        MqttManagerFactory.removeMqttManager("test_recv");
//    }
//
//    public MutableLiveData<Distance> getData() {
//
//        return data;
//    }
//
////    @Override
////    public void onMessageReceived(@NonNull MqttMessage message) {
////        try {
////            Log.i("setting--->mqtt", "收到消息： " + new String(message.getPayload()) + "\tToString:" + message.toString());
////
////            String sub_data = new String(message.getPayload());
////            JSONObject resp = new JSONObject(sub_data);
////            Distance distance = new Distance();
////            distance.setDistance1(resp.getString("soilTemperature"));
////            distance.setDistance2(resp.getString("soilHumidity"));
////            distance.setDistance3(resp.getString("airTemp"));
////            distance.setDistance4(resp.getString("airHumidity"));
////            data.setValue(distance);
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
//
//
////    @Override
////    public void settingOnMessageReceived(MqttMessage message) throws JSONException {
////        try {
////            Log.i("setting--->mqtt", "收到消息： " + new String(message.getPayload()) + "\tToString:" + message.toString());
////
////            String sub_data = new String(message.getPayload());
////            JSONObject resp = new JSONObject(sub_data);
////            Distance distance = new Distance();
////            distance.setDistance1(resp.getString("soilTemperature"));
////            distance.setDistance2(resp.getString("soilHumidity"));
////            distance.setDistance3(resp.getString("airTemp"));
////            distance.setDistance4(resp.getString("airHumidity"));
////            data.setValue(distance);
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        manager.removeOnMessageReceivedListener(); // 移除回调接口
//    }
//
//}
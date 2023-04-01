package com.atinytot.vegsp_v_1.ui.display;

import androidx.lifecycle.ViewModelProvider.Factory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.atinytot.vegsp_v_1.databinding.FragmentDisplayBinding;
import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory;
import com.atinytot.vegsp_v_1.manage.MqttManager;
import com.atinytot.vegsp_v_1.mould_item.Display_item;
import com.google.android.material.button.MaterialButton;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DisplayFragment extends Fragment {

    private static DisplayFragment displayFragment;
    private FragmentDisplayBinding binding;
    private DisplayViewModel displayViewModel;

    /** 阿里云连接参数 **/
    private final static String PRODUCTKEY = "gta2ifaeh8F";
    private final static String DEVICENAME = "test_recv";
    private final static String DEVICESECRET = "8b5d43589328b10b3cb704ad0a0323d4";

    private MaterialButton open_img;
    private Display_item soil_temp, soil_humidity, air_temp, air_humidity, hv, CO2, height, transmittance;
    private TextView temp_tv;

    private DisplayFragment() {
    }

    // 懒汉式
    public static DisplayFragment newInstance() {
        if (displayFragment == null) {
            synchronized (DisplayFragment.class) {
                if (displayFragment == null) {
                    displayFragment = new DisplayFragment();
                }
            }
        }

        return displayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SavedStateHandle handle = new SavedStateHandle();
        handle.set("productKey", PRODUCTKEY);
        handle.set("deviceName", DEVICENAME);
        handle.set("deviceSecret", DEVICESECRET);
        // 在onCreate实例化displayViewModel，防止内存泄漏，确保fragment重建数据不丢失
        displayViewModel = new ViewModelProvider(this,
                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
                .get(DisplayViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDisplayBinding.inflate(inflater, container, false);

        // TODO 实例化
        initView();

        // TODO 连接阿里云
        displayViewModel.connect();

        // TODO 数据监听
        displayViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Environment>() {
            @Override
            public void onChanged(Environment e) {
//                temp_tv.setText(s);
                soil_temp.setData(e.getSoil_temp());
                soil_humidity.setData(e.getSoil_humidity());
                air_temp.setData(e.getAir_temp());
                air_humidity.setData(e.getAir_humidity());
                hv.setData(e.getHv());
                CO2.setData(e.getCO2());
            }
        });

        open_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                displayViewModel.setData(String.valueOf(new Date().getTime()));
//                Toast.makeText(getContext(), finalMqttMessage.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * 实例化UI控件
     */
    private void initView() {

        open_img = binding.openImg;

        // 实例化 参数控件
        soil_temp = (Display_item) binding.soilTemp;
        soil_humidity = (Display_item) binding.soilHumidity;
        air_temp = (Display_item) binding.airTemp;
        air_humidity = (Display_item) binding.airHumidity;
        hv = (Display_item) binding.hv;
        CO2 = (Display_item) binding.CO2;

//        temp_tv = binding.tempTv;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        displayViewModel.disconnect();
        displayViewModel.onCleared();
    }
}
package com.atinytot.vegsp_v_1.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.databinding.FragmentSettingBinding;
import com.atinytot.vegsp_v_1.domain.Distance;
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory;
import com.atinytot.vegsp_v_1.mould_item.Display_item;
import com.atinytot.vegsp_v_1.view.ChartMould;
import com.github.mikephil.charting.charts.LineChart;

import androidx.lifecycle.ViewModelProvider.Factory;

import java.util.Random;

public class SettingFragment extends Fragment {

    private static SettingFragment settingFragment;
    private FragmentSettingBinding binding;
    private SettingViewModel settingViewModel;
    private Display_item distance1, distance2, distance3, distance4;

    // TODO 画图对象
    private LineChart chart;
    private ChartMould chartMould;
    // TODO 画图线程
    private static Thread chartThread;

    /** 阿里云连接参数 **/
    private final static String PRODUCTKEY = "gta2ifaeh8F";
    private final static String DEVICENAME = "test_recv";
    private final static String DEVICESECRET = "8b5d43589328b10b3cb704ad0a0323d4";

    private SettingFragment() {}

    public static SettingFragment newInstance() {
        if (settingFragment == null) {
            synchronized (SettingFragment.class) {
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
            }
        }

        return settingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SavedStateHandle handle = new SavedStateHandle();
        handle.set("productKey", PRODUCTKEY);
        handle.set("deviceName", DEVICENAME);
        handle.set("deviceSecret", DEVICESECRET);
        // 在onCreate实例化displayViewModel，防止内存泄漏，确保fragment重建数据不丢失
        settingViewModel = new ViewModelProvider(this,
                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
                .get(SettingViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);

        // TODO 实例化
        initPlantChart();
        // TODO 连接阿里云
        settingViewModel.connect();
        // TODO 开始绘图
        Random random = new Random();
        settingViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Distance>() {
            @Override
            public void onChanged(Distance distance) {
                distance1.setData(distance.getDistance1());
                distance2.setData(distance.getDistance2());
                distance3.setData(distance.getDistance3());
                distance4.setData(distance.getDistance4());
                float temp_float = (float) ((random.nextFloat()) * 5) + 30;
                chartMould.addEntry(chart, temp_float);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 实例化
     */
    private void initPlantChart() {
        // TODO 画图对象
        chart = binding.plantChart;
        // TODO 初始化折线图
        chartMould = new ChartMould(chart);
        // TODO 距离
        distance1 = binding.distance1;
        distance2 = binding.distance2;
        distance3 = binding.distance3;
        distance4 = binding.distance4;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        settingViewModel.disconnect();
        settingViewModel.onCleared();
    }
}

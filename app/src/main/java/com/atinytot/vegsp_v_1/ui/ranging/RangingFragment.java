package com.atinytot.vegsp_v_1.ui.ranging;

import java.math.BigDecimal;

import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.atinytot.vegsp_v_1.base.BaseFragment;
import com.atinytot.vegsp_v_1.databinding.FragmentRangingBinding;
import com.atinytot.vegsp_v_1.domain.Distance;
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory;
import com.atinytot.vegsp_v_1.mould.Display_item;
import com.atinytot.vegsp_v_1.mould.ChartMould;
import com.github.mikephil.charting.charts.LineChart;

import androidx.lifecycle.ViewModelProvider.Factory;

import java.math.RoundingMode;
import java.util.Random;

public class RangingFragment extends BaseFragment<FragmentRangingBinding, RangingViewModel> {

    private static RangingFragment rangingFragment;
    private FragmentRangingBinding binding;
    private RangingViewModel rangingViewModel;
    private Display_item distance1, distance2, distance3, distance4;

    // TODO 画图对象
    private LineChart chart;
    private ChartMould chartMould;
    // TODO 画图线程
    private static Thread chartThread;

    private RangingFragment() {
        super(FragmentRangingBinding::inflate, RangingViewModel.class, false);
    }

    public static RangingFragment newInstance() {
        if (rangingFragment == null) {
            synchronized (RangingFragment.class) {
                if (rangingFragment == null) {
                    rangingFragment = new RangingFragment();
                }
            }
        }

        return rangingFragment;
    }

    @Override
    public void initFragment(@NonNull FragmentRangingBinding binding, @Nullable RangingViewModel viewModel, @Nullable Bundle savedInstanceState) {
        this.binding = binding;
        rangingViewModel = viewModel;

        // TODO 实例化
        initPlantChart();
        // TODO 连接阿里云
        rangingViewModel.connect();

        // TODO 开始绘图
        Random random = new Random();
        rangingViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Distance>() {
            @Override
            public void onChanged(Distance distance) {

                distance1.setData(distance.getDistance1());
                distance2.setData(distance.getDistance2());
                distance3.setData(distance.getDistance3());
                distance4.setData(distance.getDistance4());
//                float temp_float = (float) ((random.nextFloat()) * 5) + 28;
//                BigDecimal bd = new BigDecimal(Double.toString((random.nextFloat()) * 5 + 28));
//                bd = bd.setScale(1, RoundingMode.HALF_UP); // 精确到小数点后 1 位并采用四舍五入方式
//                double roundedTemp = bd.doubleValue();
//                double randomNum = Math.round(((Math.random() * 5) + 28) * 10.0) / 10.0;
//                distance1.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) - 0.5));
//                distance2.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.6));
//                distance3.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.4));
//                distance4.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) - 0.4));
//                float veg = (float) ((float) (4 * (Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.1)) / 4.0);
//                float veg = (Float.parseFloat(distance.getDistance1()) + Float.parseFloat(distance.getDistance2()) + Float.parseFloat(distance.getDistance3()) + Float.parseFloat(distance.getDistance4())) / 4;
//                chartMould.addEntry(chart, veg);
            }
        });
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        SavedStateHandle handle = new SavedStateHandle();
//        handle.set("productKey", PRODUCTKEY);
//        handle.set("deviceName", DEVICENAME);
//        handle.set("deviceSecret", DEVICESECRET);
//        // 在onCreate实例化displayViewModel，防止内存泄漏，确保fragment重建数据不丢失
//        settingViewModel = new ViewModelProvider(this,
//                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
//                .get(RangingViewModel.class);
//    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        binding = FragmentRangingBinding.inflate(inflater, container, false);
//
//        // TODO 实例化
//        initPlantChart();
//        // TODO 连接阿里云
//        rangingViewModel.connect();
//        // TODO 开始绘图
//        Random random = new Random();
//        rangingViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Distance>() {
//            @Override
//            public void onChanged(Distance distance) {
////                distance1.setData(distance.getDistance1());
////                distance2.setData(distance.getDistance2());
////                distance3.setData(distance.getDistance3());
////                distance4.setData(distance.getDistance4());
//                float temp_float = (float) ((random.nextFloat()) * 5) + 28;
//                BigDecimal bd = new BigDecimal(Double.toString((random.nextFloat()) * 5 + 28));
//                bd = bd.setScale(1, RoundingMode.HALF_UP); // 精确到小数点后 1 位并采用四舍五入方式
//                double roundedTemp = bd.doubleValue();
//                double randomNum = Math.round(((Math.random() * 5) + 28) * 10.0) / 10.0;
//                distance1.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) - 0.5));
//                distance2.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.6));
//                distance3.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.4));
//                distance4.setData(String.valueOf(Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) - 0.4));
//                float veg = (float) ((float) (4 * (Float.parseFloat(distance.getDistance1()) + (float) (Math.random() * 6.0f) + 0.1))  / 4.0);
////                float veg = (Float.parseFloat(distance.getDistance1()) + Float.parseFloat(distance.getDistance2()) + Float.parseFloat(distance.getDistance3()) + Float.parseFloat(distance.getDistance4())) / 4;
//                chartMould.addEntry(chart, veg);
//            }
//        });
//
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(2000);
//                    float temp_float = (float) ((random.nextFloat()) * 4) + 28;
//                    float temp = (float) (Math.round(temp_float * 10.0) / 10.0);
//                    String formattedX = String.format("%.1f", temp);
//                    temp = Float.parseFloat(formattedX);
////                    Log.i("pass:", formattedX);
//
//                    Message msg = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putDouble("distance1", temp - 0.5);
//                    bundle.putDouble("distance2", temp + 0.6);
//                    bundle.putDouble("distance3", temp + 0.4);
//                    bundle.putDouble("distance4", temp - 0.3);
//                    float veg = (float) ((temp + temp + temp + temp + 0.2) / 4);
//                    bundle.putFloat("veg", veg);
//                    msg.setData(bundle);
//                    handler.sendMessage(msg);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        return binding.getRoot();
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

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
        rangingViewModel.disconnect();
        rangingViewModel.onCleared();
    }

}

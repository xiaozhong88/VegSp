package com.atinytot.vegsp_v_1.ui.display;

import static java.lang.Thread.sleep;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.viewbinding.ViewBinding;

import com.atinytot.vegsp_v_1.base.BaseFragment;
import com.atinytot.vegsp_v_1.databinding.FragmentDisplayBinding;
import com.atinytot.vegsp_v_1.domain.Environment;
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory;
import com.atinytot.vegsp_v_1.mould.MyWaveProgress;
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function3;

public class DisplayFragment extends BaseFragment<FragmentDisplayBinding, DisplayViewModel> {

//    public DisplayFragment(@NonNull Function3<? super LayoutInflater, ? super ViewGroup, ? super Boolean, ? extends FragmentDisplayBinding> inflate,
//                           @Nullable Class<DisplayViewModel> viewModelClass,
//                           boolean publicViewModelTag) {
//        super(inflate, viewModelClass, publicViewModelTag);
//    }

    private static DisplayFragment displayFragment = null;
    private FragmentDisplayBinding binding;
    private DisplayViewModel displayViewModel;

    private MaterialButton open_img, renovate_btn;
    //    private Display_item soil_temp, soil_humidity, air_temp, air_humidity, hv, CO2, height, transmittance;
    private MaterialTextView soil_temp, soil_humidity, air_temp, air_humidity, hv, CO2, height, transmittance, marquee_text;
    private PointerSpeedometer soiltempPointerSpeedometer, airTempPointerSpeedometer;
    private MyWaveProgress soilHumidityProgress, hvProgress, airHumidityProgress, CO2Progress;
    private TextView temp_tv, hintText;

    public DisplayFragment() {
        super(FragmentDisplayBinding::inflate, DisplayViewModel.class, false);
    }

    // TODO 懒汉式
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
    public void initFragment(@NonNull FragmentDisplayBinding binding, @Nullable DisplayViewModel viewModel, @Nullable Bundle savedInstanceState) {

        this.binding = binding;
        displayViewModel = viewModel;

        // TODO 实例化
        initView();

//        marquee_text.setSelected(true);

        // TODO 连接阿里云
        boolean state = displayViewModel.connect();
        if (state) {
            // TODO 数据监听
            displayViewModel.getData().observe(this, new Observer<Environment>() {
                @Override
                public void onChanged(Environment e) {
                    if (e.getSoil_temp() != null) {
                        soil_temp.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            soil_temp.setText(e.getSoil_temp());
                            soil_temp.setAlpha(0f);
                            soil_temp.animate().alpha(1f).setDuration(500).start();
                        }).start();
                        soil_humidity.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            soil_humidity.setText(e.getSoil_humidity());
                            soil_humidity.setAlpha(0f);
                            soil_humidity.animate().alpha(1f).setDuration(500).start();
                        }).start();
                        air_temp.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            air_temp.setText(e.getAir_temp());
                            air_temp.setAlpha(0f);
                            air_temp.animate().alpha(1f).setDuration(500).start();
                        }).start();
                        air_humidity.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            air_humidity.setText(e.getAir_humidity());
                            air_humidity.setAlpha(0f);
                            air_humidity.animate().alpha(1f).setDuration(500).start();
                        }).start();
                        hv.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            hv.setText(e.getHv());
                            hv.setAlpha(0f);
                            hv.animate().alpha(1f).setDuration(500).start();
                        }).start();
                        CO2.animate().alpha(0f).setDuration(500).withEndAction(() -> {
                            CO2.setText(e.getCO2());
                            CO2.setAlpha(0f);
                            CO2.animate().alpha(1f).setDuration(500).start();
                        }).start();

                        airHumidityProgress.setValue(Float.parseFloat(e.getAir_humidity().trim()));
                        airTempPointerSpeedometer.speedTo(Float.parseFloat(e.getAir_temp().trim()), 1000);
                        CO2Progress.setValue(Float.parseFloat(e.getCO2().trim()));
                        soilHumidityProgress.setValue(Float.parseFloat(e.getSoil_humidity().trim()));
                        soiltempPointerSpeedometer.speedTo(Float.parseFloat(e.getSoil_temp().trim()), 1000);
                        hvProgress.setValue(Float.parseFloat(e.getHv().trim()));
                    }
                }
            });
        }

    }


//    private static DisplayFragment displayFragment;
//    private FragmentDisplayBinding binding;
//    private DisplayViewModel displayViewModel;
//
//    /**
//     * 阿里云连接参数
//     **/
//    private final static String PRODUCTKEY = "gta2ifaeh8F";
//    private final static String DEVICENAME = "test_recv";
//    private final static String DEVICESECRET = "8b5d43589328b10b3cb704ad0a0323d4";
//
//    private MaterialButton open_img, renovate_btn;
//    //    private Display_item soil_temp, soil_humidity, air_temp, air_humidity, hv, CO2, height, transmittance;
//    private MaterialTextView soil_temp, soil_humidity, air_temp, air_humidity, hv, CO2, height, transmittance;
//    private PointerSpeedometer soiltempPointerSpeedometer, airTempPointerSpeedometer;
//    private MyWaveProgress soilHumidityProgress, hvProgress, airHumidityProgress, CO2Progress;
//    private TextView temp_tv, hintText, marquee_text;
//
//    private DisplayFragment() {
//    }
//
//    // 懒汉式
//    public static DisplayFragment newInstance() {
//        if (displayFragment == null) {
//            synchronized (DisplayFragment.class) {
//                if (displayFragment == null) {
//                    displayFragment = new DisplayFragment();
//                }
//            }
//        }
//
//        return displayFragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        SavedStateHandle handle = new SavedStateHandle();
//        handle.set("productKey", PRODUCTKEY);
//        handle.set("deviceName", DEVICENAME);
//        handle.set("deviceSecret", DEVICESECRET);
//        // 在onCreate实例化displayViewModel，防止内存泄漏，确保fragment重建数据不丢失
////        displayViewModel = new ViewModelProvider(this,
////                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
////                .get(DisplayViewModel.class);
//        displayViewModel = new ViewModelProvider(this,
//                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
//                .get(DisplayViewModel.class);
//    }
//
//    private float preValue = 0;
//    private ValueAnimator animator;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        binding = FragmentDisplayBinding.inflate(inflater, container, false);
//
//        // TODO 实例化
//        initView();
//
//        // TODO 连接阿里云
//        displayViewModel.connect();
//
//        marquee_text = binding.marqueeText;
//        marquee_text.setSelected(true);
//
//        List<MaterialTextView> textViews = new ArrayList<>();
//        textViews.add(soil_temp);
//        textViews.add(soil_humidity);
//        textViews.add(air_temp);
//        textViews.add(air_humidity);
//        textViews.add(hv);
//        textViews.add(CO2);
//
//        animator = ValueAnimator.ofFloat(1f, 0f);
//        animator.setDuration(500);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message message) {
//                CharSequence charSequence = message.getData().getCharSequence("str");
//                int i = message.getData().getInt("int");
//                // 添加一个动画更新监听器，用于在动画更新时更新UI
////                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////                    @Override
////                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
////                        float value = (float) valueAnimator.getAnimatedValue();
////                        for (MaterialTextView textview :
////                                textViews) {
////                            textview.setAlpha(value);
////                        }
////                    }
////                });
////                animator.addListener(new AnimatorListenerAdapter() {
////
////                    @Override
////                    public void onAnimationEnd(Animator animation) {
////                        for (MaterialTextView textView : textViews) {
////                            textView.setText(String.valueOf(i * 10));
////                            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
////                            fadeIn.setDuration(500);
////                            fadeIn.start();
////                        }
////                    }
////                });
////
////                // 启动动画
////                animator.start();
//
////                ValueAnimator animator = ValueAnimator.ofInt((int) preValue, i * 10);
////                for (MaterialTextView textview :
////                        textViews) {
////                    ObjectAnimator animator = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f);
////                    animator.setDuration(1000);
////                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
////                    animator.addListener(new AnimatorListenerAdapter() {
////
////                        @Override
////                        public void onAnimationEnd(Animator animation) {
////                            textview.setText(String.valueOf(i * 10));
////                            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textview, "alpha", 0f, 1f);
////                            fadeIn.setDuration(200);
////                            fadeIn.start();
////                        }
////
//////                    @Override
//////                    public void onAnimationUpdate(ValueAnimator animation) {
//////                        int value = (int) animation.getAnimatedValue();
//////                        soil_temp.setText(String.valueOf(value));
//////                        soil_humidity.setText(String.valueOf(value));
//////                        air_temp.setText(String.valueOf(value));
//////                        air_humidity.setText(String.valueOf(value));
//////                        hv.setText(String.valueOf(value));
//////                        CO2.setText(String.valueOf(value));
//////                        preValue = value;
//////                    }
////
////                    });
////                    animator.start();
////                }
//                soil_temp.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    soil_temp.setText(String.valueOf(i * 10));
//                    soil_temp.setAlpha(0f);
//                    soil_temp.animate().alpha(1f).setDuration(500).start();
//                }).start();
//                soil_humidity.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    soil_humidity.setText(String.valueOf(i * 10));
//                    soil_humidity.setAlpha(0f);
//                    soil_humidity.animate().alpha(1f).setDuration(500).start();
//                }).start();
//                air_temp.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    air_temp.setText(String.valueOf(i * 10));
//                    air_temp.setAlpha(0f);
//                    air_temp.animate().alpha(1f).setDuration(500).start();
//                }).start();
//                air_humidity.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    air_humidity.setText(String.valueOf(i * 10));
//                    air_humidity.setAlpha(0f);
//                    air_humidity.animate().alpha(1f).setDuration(500).start();
//                }).start();
//                hv.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    hv.setText(String.valueOf(17000 + i * 100));
//                    hv.setAlpha(0f);
//                    hv.animate().alpha(1f).setDuration(500).start();
//                }).start();
//                CO2.animate().alpha(0f).setDuration(500).withEndAction(() -> {
//                    CO2.setText(String.valueOf(i * 10));
//                    CO2.setAlpha(0f);
//                    CO2.animate().alpha(1f).setDuration(500).start();
//                }).start();
//
//                airHumidityProgress.setValue(i * 10f);
//                airTempPointerSpeedometer.speedTo(i * 10f, 1000);
//                CO2Progress.setValue(i * 10f);
//                soilHumidityProgress.setValue(i * 10f);
//                soiltempPointerSpeedometer.speedTo(i * 10f, 1000);
//                hvProgress.setValue(17000f + i * 100);
////                ValueAnimator animator2 = ValueAnimator.ofFloat(preValue, i * 10f);
////                animator2.setDuration(1000);
////                animator2.setInterpolator(new AccelerateDecelerateInterpolator());
////                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////                    @Override
////                    public void onAnimationUpdate(ValueAnimator animation) {
////                        float value = (float) animation.getAnimatedValue();
////                        soiltempPointerSpeedometer.setSpeedAt(value);
////                        airTempPointerSpeedometer.setSpeedAt(value);
////                        preValue = value;
////                    }
////                });
////                animator2.start();
//                return true;
//            }
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < 8; i++) {
//                        String str = String.valueOf(i * 10);
//                        CharSequence charSequence = str;
//                        Message msg = new Message();
//                        Bundle b = new Bundle();
//                        b.putCharSequence("str", charSequence);
//                        b.putInt("int", i);
//                        msg.setData(b);
//                        handler.sendMessage(msg);
////                        handler.post(new Runnable() {  // 在主线程中更新 UI 控件的值
////                            @Override
////                            public void run() {
////                                handler.sendMessage(msg);
////                            }
////                        });
//                        sleep(2000);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//        // TODO 数据监听
//        displayViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Environment>() {
//            @Override
//            public void onChanged(Environment e) {
//////                soil_temp.setData(e.getSoil_temp());
//////                soil_humidity.setData(e.getSoil_humidity());
//////                air_temp.setData(e.getAir_temp());
//////                air_humidity.setData(e.getAir_humidity());
//////                hv.setData(e.getHv());
//////                CO2.setData(e.getCO2());
////
//////                temp_tv.setText(e.toString());
//                Log.i("Environment:", e.toString());
//            }
//        });
//
////        renovate_btn.setOnClickListener(view -> {
////            soil_temp.setData("1");
////            soil_humidity.setData("1");
////            air_temp.setData("1");
////            air_humidity.setData("1");
////            hv.setData("1");
////            CO2.setData("1");
////        });
//        // 长按提示
////        renovate_btn.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View view) {
////                hintText.setVisibility(View.VISIBLE);
////                return true;
////            }
////        });
//        // 焦点消失后，隐藏提示
////        renovate_btn.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View view, MotionEvent event) {
////                if (event.getAction() == MotionEvent.ACTION_UP) {
////                    hintText.setVisibility(View.INVISIBLE);
////                }
////                return false;
////            }
////        });
//
//
////        open_img.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                displayViewModel.setData(String.valueOf(new Date().getTime()));
//////                Toast.makeText(getContext(), finalMqttMessage.toString(), Toast.LENGTH_SHORT).show();
////
////            }
////        });
//
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//

    /**
     * 实例化UI控件
     */
    private void initView() {

//        open_img = binding.openImg;
//        hintText = binding.hintText;
//        renovate_btn = binding.renovateBtn;

        // 实例化 参数控件
        soil_temp = binding.soilTemp;
        soil_humidity = binding.soilHumidity;
        air_temp = binding.airTemp;
        air_humidity = binding.airHumidity;
        hv = binding.hv;
        CO2 = binding.CO2;

        soiltempPointerSpeedometer = binding.soilTempPointerSpeedometer;
        airTempPointerSpeedometer = binding.airTempPointerSpeedometer;

        soilHumidityProgress = binding.soilHumidityProgress;
        hvProgress = binding.hvProgress;
        airHumidityProgress = binding.airHumidityProgress;
        CO2Progress = binding.CO2Progress;

        marquee_text = binding.marqueeText;
//        temp_tv = binding.tempTv;
    }

    //
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        displayViewModel.disconnect();
        displayViewModel.onCleared();
    }
}
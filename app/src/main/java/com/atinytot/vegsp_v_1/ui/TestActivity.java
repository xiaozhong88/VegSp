package com.atinytot.vegsp_v_1.ui;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.databinding.TestBinding;
import com.atinytot.vegsp_v_1.mould.MyWaveProgress;
import com.atinytot.vegsp_v_1.mould.newton.NewtonCradleLoading;
import com.flod.loadingbutton.LoadingButton;
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class TestActivity extends Activity{

    private static TestBinding binding;
    private PieChart pieChart, pieChart2;
    private CircleProgress progressBar;
    private ArcProgress arcProgress;
    private PointerSpeedometer pointerSpeedometer;
    private MyWaveProgress soilHumidityProgress, hvProgress, airHumidityProgress, CO2Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = TestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("PieChartActivity");

//        LoadingButton btn = binding.btn;
        NewtonCradleLoading n = binding.newtonCradleLoading;
        n.start();
        n.setLoadingColor(R.color.teal_200);

//        soilHumidityProgress = binding.soilHumidityProgress;
//        hvProgress = binding.hvProgress;
//        airHumidityProgress = binding.airHumidityProgress;
//        CO2Progress = binding.CO2Progress;
//
//        soilHumidityProgress.setValue(40f);
//        hvProgress.setValue(16000f);
//        airHumidityProgress.setValue(40f);
//        CO2Progress.setValue(40f);

//        pieChart = binding.chart1;
//        pieChart2 = binding.chart2;
//        progressBar = binding.soilHumidityProgress;
//        arcProgress = binding.arcProgress;
//        pointerSpeedometer = binding.pointerSpeedometer;

//        pieChart2.getLayoutParams().width = (int) (pieChart.getLayoutParams().width * 0.5);
//        pieChart2.getLayoutParams().height = (int) (pieChart.getLayoutParams().height * 0.5);

//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart2.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5, 10, 5, 5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//
//
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColor(Color.WHITE);
//
//        pieChart.setTransparentCircleColor(Color.WHITE);
//        pieChart.setTransparentCircleAlpha(110);
//
//        pieChart.setHoleRadius(58f);
//        pieChart.setTransparentCircleRadius(63f);
//
//        pieChart.setDrawCenterText(true);
//
//        pieChart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        pieChart.setRotationEnabled(true);
//        pieChart2.setRotationEnabled(false);
//        pieChart.setHighlightPerTapEnabled(true);
//
//        pieChart.animateY(1400, Easing.EaseInOutQuad);
//        // chart.spin(2000, 0, 360);
//
////        Legend l = pieChart.getLegend();
////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
////        l.setOrientation(Legend.LegendOrientation.VERTICAL);
////        l.setDrawInside(false);
////        l.setXEntrySpace(7f);
////        l.setYEntrySpace(0f);
////        l.setYOffset(0f);
//        pieChart.getLegend().setEnabled(false);
//        pieChart2.getLegend().setEnabled(false);
//
//        setData(1,1);
//
//        // entry label styling
//        pieChart.setEntryLabelColor(Color.WHITE);
////        chart.setEntryLabelTypeface(tfRegular);
//        pieChart.setEntryLabelTextSize(12f);
//
//        drawTickLines();
//
//        // 设置动画时长
//        long duration = 3000;
//
//        // 创建ValueAnimator对象，设置动画范围和时长
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
//        animator.setDuration(duration);
//
//        // 设置插值器，让动画从开始到结束的过程中速度逐渐变化
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        // 设置更新监听器，更新进度条的当前进度
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float progress = (float) animation.getAnimatedValue();
//                progressBar.setProgress((int) progress);
//            }
//        });
//
//        // 启动动画
//        animator.start();
//
//        // 设置动画时长
//        long duration2 = 3000;
//
//        // 创建ValueAnimator对象，设置动画范围和时长
//        ValueAnimator animator2 = ValueAnimator.ofFloat(0, 100);
//        animator2.setDuration(duration2);
//
//        // 设置插值器，让动画从开始到结束的过程中速度逐渐变化
//        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        // 设置更新监听器，更新进度条的当前进度
//        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float progress = (float) animation.getAnimatedValue();
//                arcProgress.setProgress((int) progress);
//            }
//        });
//
//        // 启动动画
//        animator2.start();
//
//        // 设置动画时长
//        long duration3 = 3000;
//
//        // 创建ValueAnimator对象，设置动画范围和时长
//        ValueAnimator animator3 = ValueAnimator.ofFloat(-5, 60);
//        animator3.setDuration(duration3);
//
//        // 设置插值器，让动画从开始到结束的过程中速度逐渐变化
//        animator3.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        // 设置更新监听器，更新进度条的当前进度
//        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float progress = (float) animation.getAnimatedValue();
//                pointerSpeedometer.setSpeedAt(progress);
//            }
//        });
//
//        // 启动动画
//        animator3.start();
    }

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<PieEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(35, "Low"));
        entries.add(new PieEntry(25, "Medium"));
        entries.add(new PieEntry(15, "High"));
        entries.add(new PieEntry(20, ""));
        entries.add(new PieEntry(5, "Critical"));

        entries2.add(new PieEntry(100, ""));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        PieDataSet dataSet2 = new PieDataSet(entries2, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> colors2 = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(Color.TRANSPARENT);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        colors2.add(Color.GRAY);

        dataSet.setColors(colors);
        dataSet2.setColors(colors2);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        PieData data2 = new PieData(dataSet2);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tfLight);
        pieChart.setData(data);
        pieChart2.setData(data2);

        // undo all highlights
//        chart.highlightValues(null);

        pieChart.invalidate();
        pieChart2.invalidate();

    }

    private void drawTickLines() {
        // 获取PieChart的中心坐标
        float centerX = pieChart.getWidth() / 2f;
        float centerY = pieChart.getHeight() / 2f;

        // 获取饼图数据对象
        PieData data = pieChart.getData();

        if (data == null) return;

        // 获取饼图数据集合
        IPieDataSet dataSet = data.getDataSet();

        // 计算刻度线的长度
        float lineLength = 10f * 2;

        // 创建一个画笔设置刻度线的颜色和粗细
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2f);

        // 获取饼图的半径
        float radius = Math.min(pieChart.getWidth() / 2f, pieChart.getHeight() / 2f);

        // 绘制刻度线
        Canvas canvas = new Canvas();
        for (int i = 0; i < data.getEntryCount(); i++) {

            // 计算刻度线的起点和终点坐标
            float angle = pieChart.getRotationAngle() + pieChart.getDrawAngles()[i] / 2f;
            float startX = centerX + (radius + lineLength) * (float) Math.cos(Math.toRadians(angle));
            float startY = centerY + (radius + lineLength) * (float) Math.sin(Math.toRadians(angle));
            float stopX = centerX + radius * (float) Math.cos(Math.toRadians(angle));
            float stopY = centerY + radius * (float) Math.sin(Math.toRadians(angle));

            // 绘制刻度线
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }
}

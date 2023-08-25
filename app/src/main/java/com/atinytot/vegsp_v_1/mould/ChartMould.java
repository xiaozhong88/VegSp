package com.atinytot.vegsp_v_1.mould;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Locale;

public class ChartMould {

    // LineChartHelper
    private LineChart chart;

    public ChartMould(LineChart chart) {
        this.chart = chart;
        initChart();
    }

    private void initChart() {

        /* 设置描述信息 */
        Description description = new Description();
        description.setText("高度(cm)");

        chart.setDescription(description); // 设置图表描述信息
        chart.setTouchEnabled(true); // 开启触摸
        chart.setDragEnabled(true); // 开启拖动
        chart.setScaleEnabled(true); // 开启缩放

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 30));
        LineDataSet dataSet = createSet(entries);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
//        chart.getDescription().setEnabled(true);
//        chart.setTouchEnabled(true);
//
//        /* 设置描述信息 */
//        Description description = new Description();
//        description.setText("高度(cm)");
//        // TODO 获取屏幕宽度
//        // TODO 从系统服务中获取窗口管理器
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        // TODO 从默认显示器中获取显示参数保存到dm对象中
//        windowManager.getDefaultDisplay().getMetrics(dm);
//        description.setPosition((int)(dm.widthPixels / 2),48);
//        description.setTextColor(Color.RED);
//        description.setTextSize(15);
//
//        // 设置图表描述信息
//        chart.setDescription(description);
//        // 没有数据时显示的文字
//        chart.setNoDataText("没有数据呦");
//        // 没有数据时显示的文字颜色
//        chart.setNoDataTextColor(Color.RED);
//        // 绘图区后面的背景矩形将绘制
//        chart.setDrawGridBackground(false);
//        // 绘制图表表框的线
//        chart.setDrawBorders(false);
//        // 禁用缩放和开启拖动（enable scaling and dragging）
//        chart.setScaleEnabled(false);
//        chart.setDragEnabled(true);
//
//        // 是否缩放，禁用可以缩放（if disabled, scaling can be done on x- and y-axis separately）
//        chart.setPinchZoom(false);
//
//        // 代替背景色（set an alternative background color）
//        // chart.setBackgroundColor(0x4169E1FF);
//
//        LineData data = new LineData();
//        data.setValueTextColor(Color.BLACK);
//
//        // 添加空数据（add empty data）
//        chart.setData(data);
//
//        // 获取图例（get the legend (only possible after setting data)）
//        Legend l = chart.getLegend();
//
//        // 修改图例（modify the legend ...）
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTextColor(Color.BLACK);
//        // x轴配置
//        XAxis xl = chart.getXAxis();
//        // xl.setTypeface(tfLight);
//        xl.setTextColor(Color.BLACK);
//        xl.setDrawGridLines(false);
//        xl.setAvoidFirstLastClipping(true);
//        xl.setEnabled(true);
//        xl.setAxisMinimum(0f);
//        xl.setAxisMaximum(10f);
//        xl.setAxisLineWidth(2);                  // TODO 加粗
//        xl.setLabelCount(9, false); // TODO X轴坐标个数
//        // X轴文字显示位置
//        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        // 左y轴配置
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTextColor(Color.BLACK);
//        leftAxis.setAxisMaximum(40f);
//        leftAxis.setAxisMinimum(25f);
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setAxisLineWidth(2);             // TODO 加粗
//        // 右y轴配置
//        YAxis rightAxis = chart.getAxisRight();
//        rightAxis.setEnabled(false);
    }

    public void addEntry(LineChart chart, float Concentration_data) {


        // 线类
        LineData data = chart.getData();

        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet(new ArrayList<>());
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), Concentration_data), 0);
            data.notifyDataChanged();

            chart.notifyDataSetChanged();
            // TODO 设置滚动最大数量
            chart.setVisibleXRangeMaximum(10);
            chart.moveViewToX(data.getEntryCount() - 1);

            chart.animateX(500); // 添加 X 轴动画效果
            chart.invalidate(); // 刷新图表
        }

    }

    private LineDataSet createSet(ArrayList<Entry> entries) {
        // 数据集，其本身在折线图上就是一条折线
        LineDataSet set = new LineDataSet(entries, "平均高度");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.BLACK);
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65); // 透明度
//        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(8f);
        set.setDrawValues(true); // 显示数据点y值
        set.setValueFormatter(new ValueFormatter() { // 取消四舍五入
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.1f", value);
            }
        });

        set.setDrawFilled(true); // 启用填充
        set.setFillColor(Color.WHITE); // 填充白色
        return set;
    }
}

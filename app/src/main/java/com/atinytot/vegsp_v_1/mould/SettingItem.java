package com.atinytot.vegsp_v_1.mould;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.atinytot.vegsp_v_1.R;
import com.google.android.material.imageview.ShapeableImageView;

public class SettingItem extends LinearLayout {

    private ShapeableImageView item_img, item_iright;
    private TextView item_text, item_content;

    // TODO 调用其他更具体的构造函数 constructor chaining(构造函数链):本质上是一种简单的重载函数模式，避免重复调用通用初始化逻辑, 与此同时，还可以实现更简洁和维护性强的代码
    public SettingItem(Context context) {
        // TODO 调用了更具体的构造函数，使用硬编码的默认参数值
        this(context, null);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.item_setting, this, true);
        // 获取设置属性对象
        @SuppressLint("Recycle")
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);

        item_img = findViewById(R.id.item_img);
        item_text = findViewById(R.id.item_text);
        item_content = findViewById(R.id.item_content);
        item_iright = findViewById(R.id.item_iright);

        item_img.setBackgroundResource(ta.getResourceId(R.styleable.SettingItem_show_img, 0));
        item_text.setText(ta.getString(R.styleable.SettingItem_show_text));
        item_content.setText(ta.getString(R.styleable.SettingItem_show_content));
        item_iright.setBackgroundResource(ta.getResourceId(R.styleable.SettingItem_show_iright, 0));
    }

    public void setContent(String data) {
        item_content.setText(data);
    }
}

package com.atinytot.vegsp_v_1.mould;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.atinytot.vegsp_v_1.R;

public class Display_item extends LinearLayout {

    private ImageView item_unit, item_state;
    private TextView item_parameter;
    private Button item_btn;

    public Display_item(@NonNull Context context) {
        this(context, null);
    }

    public Display_item(@NonNull Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Display_item(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public Display_item(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(getContext()).inflate(R.layout.item_display, this, true);
        // 获取设置属性对象
        @SuppressLint("Recycle")
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Display_item);
        // 按钮
        item_btn = findViewById(R.id.item_btn);
        // 参数
        item_parameter = findViewById(R.id.item_parameter);
        // 单位图标
        item_unit = findViewById(R.id.item_unit);
        item_state = findViewById(R.id.item_state);
        // 获取setting_item的引用
//        rlItem = (RelativeLayout) findViewById(R.id.display_item);

        item_btn.setEnabled(ta.getBoolean(R.styleable.Display_item_parameter_btn, true));
        item_btn.setText(ta.getString(R.styleable.Display_item_btn_name));
        item_parameter.setText(ta.getString(R.styleable.Display_item_parameter_text));
//        item_text.setPadding(30,0,0,0);
//        item_unit.setBackgroundResource(ta.getResourceId(R.styleable.Display_item_unit, 0));
        item_state.setBackgroundResource(ta.getResourceId(R.styleable.Display_item_state, 0));

        ta.recycle();
    }

    public void setData(String data) {
        item_parameter.setText(data);
    }
}

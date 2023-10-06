package com.atinytot.vegsp_v_1.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.databinding.ActivitySettingBinding;
import com.atinytot.vegsp_v_1.mould.SettingItem;
import com.atinytot.vegsp_v_1.room.entity.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;
    private SettingViewModel settingViewModel;

    private SettingItem gender, revisePassword, clearCache;
    private String username, newPasswd, newPasswdAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO 初始化
        init();

        registerForContextMenu(gender);
        gender.setOnClickListener(View::showContextMenu);

        revisePassword.setOnClickListener(view -> {
            showDialog();
            Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
        });

        settingViewModel.getUserLive().observe(this, user -> {
            if (user != null) {
                if (newPasswd.equals(newPasswdAgain)) {
                    settingViewModel.updateUsers(new User(user.getUid(), username, newPasswd));
                    Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        gender = binding.gender;
        revisePassword = binding.revisePassword;
        clearCache = binding.clearCache;

        settingViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).
                get(SettingViewModel.class);
    }

    /**
     * TODO 性别菜单栏
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(getApplication());
        inflater.inflate(R.menu.gender, menu);
    }

    /**
     * TODO 菜单被点击时的页面跳转
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.man) {
            gender.setContent(item.getTitle().toString());
        } else if (itemId == R.id.woman) {
            gender.setContent(item.getTitle().toString());
        }
        return super.onContextItemSelected(item);
    }

    private void showDialog() {
        // 1. 创建一个 AlertDialog.Builder 对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. 加载自定义布局文件
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewDialog = inflater.inflate(R.layout.dialog_input, null);
        builder.setView(viewDialog);

        // 屏幕宽度和高度大小
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 3. 创建对话框并设置属性
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        // 4. 设置 Dialog 弹窗的大小和位置
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // 5. 获取 EditText 控件
        final EditText oldPassword = viewDialog.findViewById(R.id.old_password);
        final EditText newPassword = viewDialog.findViewById(R.id.new_password);
        final EditText newPasswordAgain = viewDialog.findViewById(R.id.new_password_again);

        // 6. 按钮事件监听
        Button btnOk = viewDialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_s = oldPassword.getText().toString().trim();
                newPasswd = newPassword.getText().toString().trim();
                newPasswdAgain = newPasswordAgain.getText().toString().trim();
                settingViewModel.findUser(username, old_s);
                alertDialog.dismiss();
                // Do something with the text input
            }
        });

        Button btnCancel = viewDialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * TODO 获取昵称
     * 使用sticky保证数据的接收
     * @param msg 昵称
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(String msg) {
        username = msg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}

package com.atinytot.vegsp_v_1.view

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

class App:Application() {

    override fun onCreate() {
        super.onCreate()
//        Log.e("App", "onCreate: "+ (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK))
//        EventBus.getDefault().post(ModeEvent(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // 会保留 newConfig.uiMode 的低两位（也就是白天黑夜模式位），UI_MODE_NIGHT_MASK 是一个位掩码常量
//        val newMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK

//        when (newMode) {
//            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
//            Configuration.UI_MODE_NIGHT_NO -> {
//                // 白天模式
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                Log.e("updateTheme", "updateTheme: " + "白天模式")
//            }
//
//            Configuration.UI_MODE_NIGHT_YES -> {
//                // 黑夜模式
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                Log.e("updateTheme", "updateTheme: " + "黑夜模式")
//            }
//        }
    }
}
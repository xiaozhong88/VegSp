package com.atinytot.vegsp_v_1.utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Created by VimalCvs on 02/11/2020.
 */

public class ThemeSettings {

    private static ThemeSettings instance;

    public static ThemeSettings getInstance(Context context) {
        if (instance == null) instance = new ThemeSettings(context);
        return instance;
    }

    private static class Key {
        private static final String NIGHT_MODE = "nightMode";
    }

    public boolean nightMode;

    private ThemeSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        int deviceMode = uiModeManager.getNightMode();
        if (deviceMode == UiModeManager.MODE_NIGHT_NO) {
            nightMode = prefs.getBoolean(Key.NIGHT_MODE, false);
        } else if (deviceMode == UiModeManager.MODE_NIGHT_YES) {
            nightMode = prefs.getBoolean(Key.NIGHT_MODE, true);
        }
    }

    public void save(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean(Key.NIGHT_MODE, nightMode);
        editor.apply();
    }

    public void refreshTheme() {
        AppCompatDelegate.setDefaultNightMode(nightMode ?
                AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }
}

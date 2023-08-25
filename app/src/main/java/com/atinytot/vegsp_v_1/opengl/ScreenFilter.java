package com.atinytot.vegsp_v_1.opengl;

import android.content.Context;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.utils.FileUtils;

public class ScreenFilter {

    public ScreenFilter(Context context) {
        String vertexShader = FileUtils.readRawTextFile(context, R.raw.plant_vert);
    }
}

package com.atinytot.vegsp_v_1;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class TestDemo {

    @Test
    public void randomTest() {
        Random random = new Random();
        float f = random.nextFloat() * 2 - 1;
    }
}

package com.atinytot.vegsp_v_1.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FloatUtils {

    public static FloatBuffer getFloatBuffer(float[] array, int bytes) {
        // 初始化字节缓冲区的大小=数组长度*数组元素大小
        ByteBuffer buffer = ByteBuffer.allocateDirect(array.length * bytes);
        // buffer.order：获取此缓冲区的字节顺序/ByteOrder.nativeOrder()：获取底层平台的本机字节顺序
        buffer.order(ByteOrder.nativeOrder());
        // 根据设置好的参数构造浮点缓冲区
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        // 把数组数据写入缓冲区
        floatBuffer.put(array);
        // 设置浮点缓冲区的初始位置
        floatBuffer.position(0);
        return floatBuffer;
    }
}

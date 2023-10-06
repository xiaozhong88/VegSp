package com.atinytot.vegsp_v_1.opengl

import android.content.Context
import android.content.res.AssetManager
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class MyGLSurfaceView : GLSurfaceView {

    private val renderer: MyGLRenderer

    constructor(context: Context) : this(context, null);
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

        setEGLContextClientVersion(3)
        renderer = MyGLRenderer(context, context.assets)
        setRenderer(renderer)
        // 渲染仅在收到请求后才会执行，而不是连续执行
//        renderMode = RENDERMODE_WHEN_DIRTY
    }
}
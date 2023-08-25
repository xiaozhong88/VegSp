package com.atinytot.vegsp_v_1.mould.newton

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.atinytot.vegsp_v_1.R

class CradleBall @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var ballWidth: Int = 0
    private var ballHeight: Int = 0

    private lateinit var paint: Paint

    private var loadingColor: Int = Color.WHITE

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CradleBall)
            loadingColor =
                typedArray.getColor(R.styleable.CradleBall_cradle_ball_color, Color.WHITE)
            typedArray.recycle()
        }
        paint = Paint()
        paint.color = loadingColor
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ballWidth = w
        ballHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(ballWidth / 2f, ballHeight / 2f, ballWidth / 2f, paint)
    }

    fun setLoadingColor(color: Int) {
        loadingColor = color
        paint.color = color
        postInvalidate()
    }

    fun getLoadingColor() : Int {
        return loadingColor
    }
}
package com.atinytot.vegsp_v_1.mould

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.TextViewCompat
import com.atinytot.vegsp_v_1.R

class CenterTitleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var mTitleTextAppearance = 0
    private var mTitleTextColor: ColorStateList? = null
    private var mTitleTextView: TextView? = null
    private var mTitleText: CharSequence? = null

    init {
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.CenterTitleToolbar, defStyleAttr, 0)
        mTitleTextAppearance =
            ta.getResourceId(R.styleable.CenterTitleToolbar_titleTextAppearance, 0)
        mTitleText = ta.getText(R.styleable.CenterTitleToolbar_centerTitle)
        if (!TextUtils.isEmpty(mTitleText)) {
            title = mTitleText
        }
        if (ta.hasValue(R.styleable.CenterTitleToolbar_centerTitleColor)) {
            setTitleTextColor(ta.getColorStateList(R.styleable.CenterTitleToolbar_centerTitleColor)!!)
        }
        ta.recycle()
    }

    override fun setTitle(title: CharSequence?) {
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                mTitleTextView = AppCompatTextView(context).apply {
                    isSingleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    addView(
                        this,
                        LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                        )
                    )
                }
            }
            if (mTitleTextAppearance != 0) {
                mTitleTextView?.apply {
                    TextViewCompat.setTextAppearance(this, mTitleTextAppearance)
                }
            }
            if (mTitleTextColor != null) {
                mTitleTextView?.setTextColor(mTitleTextColor)
            }
        } else if (mTitleTextView != null) {
            removeView(mTitleTextView)
        }
        mTitleTextView?.text = title
        mTitleText = title
    }

    override fun getTitle(): CharSequence? {
        return mTitleText
    }

    override fun setTitleTextColor(@NonNull color: ColorStateList) {
        mTitleTextColor = color
        mTitleTextView?.setTextColor(color)
    }

    override fun setTitleTextAppearance(context: Context?, resId: Int) {
        mTitleTextAppearance = resId
        mTitleTextView?.apply {
            TextViewCompat.setTextAppearance(this, resId)
        }
    }
}
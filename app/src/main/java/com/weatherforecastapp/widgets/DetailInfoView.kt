package com.weatherforecastapp.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.weatherforecastapp.R
import com.weatherforecastapp.extensions.getAttributes
import kotlinx.android.synthetic.main.view_detail_info.view.*

class DetailInfoView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {
        orientation = VERTICAL
        View.inflate(context, R.layout.view_detail_info, this)
        getAttributes(attrs, R.styleable.DetailInfo) {
            icon.setImageResource(getResourceId(R.styleable.DetailInfo_android_src, 0))
            info.text = getString(R.styleable.DetailInfo_hint)
        }
    }

    fun setValue(text: String) {
        value.text = text
    }
}
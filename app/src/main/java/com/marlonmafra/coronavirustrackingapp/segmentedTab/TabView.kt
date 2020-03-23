package com.marlonmafra.coronavirustrackingapp.segmentedTab

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.marlonmafra.coronavirustrackingapp.R

open abstract class TabView(
    private val context: Context,
    layout: Int
) {
    private val radius = 5
    private val strokeSize = 1
    val view: View = View.inflate(context, layout, null)
    private val txtTitle: TextView

    init {
        txtTitle = view.findViewById(R.id.txtTitle)
    }

    fun setBackground(
        colorSelected: Int,
        colorUnselected: Int,
        colorBorderSelected: Int,
        colorBorderUnselected: Int
    ) {
        view.background = getDrawableStates(
            colorSelected,
            colorUnselected,
            colorBorderSelected,
            colorBorderUnselected
        )
    }

    fun setSelected(selected: Boolean) {
        view.isSelected = selected
    }

    fun setTitle(title: String?) {
        txtTitle.text = title
    }

    fun setTitle(title: Int) {
        txtTitle.setText(title)
    }

    fun setTextSize(size: Float) {
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setTextColorState(colorState: ColorStateList?) {
        txtTitle.setTextColor(colorState)
    }

    fun setFontTitle(typeFace: Typeface?) {
        txtTitle.typeface = typeFace
    }

    private fun getDrawableStates(
        colorSelected: Int,
        colorUnselected: Int,
        colorBorderSelected: Int,
        colorBorderUnselected: Int
    ): StateListDrawable {
        val stateListDrawable =
            StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_pressed),
            getDrawable(colorSelected, colorBorderSelected)
        )
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_focused),
            getDrawable(colorSelected, colorBorderSelected)
        )
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_selected),
            getDrawable(colorSelected, colorBorderSelected)
        )
        stateListDrawable.addState(
            intArrayOf(),
            getDrawable(colorUnselected, colorBorderUnselected)
        )
        return stateListDrawable
    }

    private fun getDrawable(backgroundColor: Int, strokeColor: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(backgroundColor)
        gradientDrawable.cornerRadii = getCornerRadii(
            Utils.getValueByDensity(
                context,
                radius
            )
        )
        gradientDrawable.setStroke(
            Utils.getValueByDensity(
                context,
                strokeSize
            ).toInt(), strokeColor
        )
        return gradientDrawable
    }

    protected abstract fun getCornerRadii(roundRadius: Float): FloatArray
}
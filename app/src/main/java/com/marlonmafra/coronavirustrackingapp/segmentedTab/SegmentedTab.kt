package com.marlonmafra.coronavirustrackingapp.segmentedTab

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.marlonmafra.coronavirustrackingapp.R

class SegmentedTab : TabLayout {
    private var tabSelectedColor = 0
    private var tabUnselectedColor = 0
    private var titleColor: ColorStateList? = null
    private var titleTextSize = 12
    private var typeface: Typeface? = null
    private var borderColorSelected = 0
    private var borderColorUnselected = 0

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        setBackgroundResource(R.drawable.tab_background_color_selector)
        setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                context,
                android.R.color.transparent
            )
        )
        tabMode = MODE_FIXED
        tabGravity = GRAVITY_FILL
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.SegmentedTab)
            tabSelectedColor = typedArray.getColor(
                R.styleable.SegmentedTab_tabSelectedColor,
                ContextCompat.getColor(context, R.color.white)
            )
            tabUnselectedColor = typedArray.getColor(
                R.styleable.SegmentedTab_tabUnselectedColor,
                ContextCompat.getColor(context, android.R.color.white)
            )
            titleColor = if (typedArray.hasValue(R.styleable.SegmentedTab_titleColor)) {
                typedArray.getColorStateList(R.styleable.SegmentedTab_titleColor)
            } else {
                ContextCompat.getColorStateList(context, R.color.segment_control_title)
            }
            titleTextSize = typedArray.getDimensionPixelSize(
                R.styleable.SegmentedTab_titleTextSize,
                titleTextSize
            )
            borderColorSelected = typedArray.getColor(
                R.styleable.SegmentedTab_borderColorSelected,
                tabSelectedColor
            )
            borderColorUnselected = typedArray.getColor(
                R.styleable.SegmentedTab_borderColorUnselected,
                tabSelectedColor
            )
            val fontId = typedArray.getResourceId(
                R.styleable.SegmentedTab_titleFontPath,
                R.font.rubik_regular
            )
            typeface = ResourcesCompat.getFont(context, fontId)
            typedArray.recycle()
        }
        disablePadding("tabPaddingStart")
        disablePadding("tabPaddingEnd")
    }

    fun setup(titles: List<Int>) {
        require(titles.size > 1) { "You need at least two tabs" }
        for (i in 0 until tabCount) {
            val tab = getTabAt(i)
            if (tab != null) {
                tab.customView = getTabView(i, titles)
            }
        }
        val layoutParams =
            layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(
            Utils.getValueByDensity(
                context,
                20
            ).toInt(),
            layoutParams.topMargin,
            Utils.getValueByDensity(
                context,
                20
            ).toInt(),
            layoutParams.bottomMargin
        )
    }

    private fun getTabView(position: Int, titles: List<Int>): View {
        val tab: com.marlonmafra.coronavirustrackingapp.segmentedTab.TabView
        when (position) {
            0 -> {
                tab = LeftTabView(context, R.layout.left_tab)
                tab.setSelected(true)
            }
            titles.size - 1 -> {
                tab = RightTabView(context, R.layout.right_tab)
            }
            else -> {
                tab = CenterTabView(context, R.layout.center_tab)
            }
        }
        tab.setBackground(
            tabSelectedColor,
            tabUnselectedColor,
            borderColorSelected,
            borderColorUnselected
        )
        tab.setTitle(titles[position])
        tab.setTextSize(titleTextSize.toFloat())
        tab.setTextColorState(titleColor)
        typeface?.let { tab.setFontTitle(typeface) }

        return tab.view
    }

    private fun disablePadding(name: String) {
        try {
            val mTabPaddingStart = TabLayout::class.java.getDeclaredField(name)
            mTabPaddingStart.isAccessible = true
            mTabPaddingStart.setInt(this, Utils.getValueByDensity(context, -0.5).toInt())
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}
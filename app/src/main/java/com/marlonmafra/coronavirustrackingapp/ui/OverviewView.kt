package com.marlonmafra.coronavirustrackingapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.marlonmafra.coronavirustrackingapp.R
import kotlinx.android.synthetic.main.overview_view.view.labelText
import kotlinx.android.synthetic.main.overview_view.view.valueText

const val defaultLabel = R.string.label
const val defaultValue = R.string.value
const val defaultLabelColor = R.color.white
const val defaultValueColor = R.color.slate_grey_20

class OverviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.overview_view, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.OverviewView, defStyleAttr, 0)
        with(typedArray) {
            val label = getResourceId(R.styleable.OverviewView_label, defaultLabel)
            labelText.setText(label)

            val labelColor =
                getResourceId(R.styleable.OverviewView_labelTextColor, defaultLabelColor)
            labelText.setTextColor(ContextCompat.getColor(context, labelColor))

            getString(R.styleable.OverviewView_value)?.let {
                valueText.text = it
            }

            val valueColor =
                getResourceId(R.styleable.OverviewView_valueTextColor, defaultValueColor)
            valueText.setTextColor(ContextCompat.getColor(context, valueColor))

            recycle()
        }
    }

    fun setValue(value: String) {
        valueText.text = value
    }
}

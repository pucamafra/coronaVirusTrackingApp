package com.marlonmafra.coronavirustrackingapp.features.home.countries

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.format
import com.marlonmafra.coronavirustrackingapp.model.Location
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.country_list_item.confirmedNumber
import kotlinx.android.synthetic.main.country_list_item.countryName
import kotlinx.android.synthetic.main.country_list_item.flag

class CountryListItem(
    private val location: Location
) : AbstractFlexibleItem<CountryListItem.ViewHolder>(), IFilterable<String> {

    override fun equals(other: Any?): Boolean = when (other) {
        is CountryListItem -> location == other.location
        else -> false
    }

    override fun hashCode(): Int = location.hashCode()

    override fun getLayoutRes(): Int = R.layout.country_list_item

    override fun filter(constraint: String): Boolean {
        return location.country.contains(constraint, true)
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<*>>): ViewHolder =
        ViewHolder(view, adapter)

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: List<Any>
    ) = holder.bind(location)

    inner class ViewHolder(override val containerView: View, adapter: FlexibleAdapter<*>) :
        FlexibleViewHolder(containerView, adapter), LayoutContainer {

        fun bind(location: Location) {
            when {
                location.province.isEmpty() -> countryName.text = location.country
                else -> countryName.text = "${location.country}/${location.province}"
            }

            confirmedNumber.text = location.latest.confirmed.format()
            flag.setImageResource(World.getFlagOf(location.countryCode))
        }
    }
}

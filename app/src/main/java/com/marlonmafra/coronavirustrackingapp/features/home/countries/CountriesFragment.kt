package com.marlonmafra.coronavirustrackingapp.features.home.countries

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.features.home.HomeViewModel
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import kotlinx.android.synthetic.main.fragment_countries.countryList
import kotlinx.android.synthetic.main.fragment_countries.searchEditText
import java.util.ArrayList

class CountriesFragment : Fragment() {

    companion object {
        fun newInstance() = CountriesFragment()
    }

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: FlexibleAdapter<AbstractFlexibleItem<*>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_countries, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.locations.observe(requireActivity(), Observer { response ->
            setupLayout(response)
        })

        adapter = FlexibleAdapter(ArrayList<AbstractFlexibleItem<*>>())
        adapter.isAnimateChangesWithDiffUtil = true
        countryList.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        countryList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.country_divider
            )!!
        )
        countryList.addItemDecoration(dividerItemDecoration)
    }

    private fun setupLayout(response: TrackingResponse) = with(response) {
        val items = mutableListOf<AbstractFlexibleItem<*>>()
        adapter.clear()
        response.locations.forEach {
            items.add(CountryListItem(it))
        }
        adapter.updateDataSet(items)

        searchEditText.addTextChangedListener(textWatcher())
    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no-imp
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchTermEntered(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // no-imp
            }
        }
    }

    private fun searchTermEntered(constraint: String) {
        adapter.setFilter(constraint)
        adapter.filterItems()
    }
}

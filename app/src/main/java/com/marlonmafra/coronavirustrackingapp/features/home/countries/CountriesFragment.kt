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
import com.marlonmafra.coronavirustrackingapp.features.details.CountryDetails
import com.marlonmafra.coronavirustrackingapp.features.home.HomeViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import kotlinx.android.synthetic.main.fragment_countries.countryList
import kotlinx.android.synthetic.main.fragment_countries.searchEditText

class CountriesFragment : Fragment() {

    companion object {
        fun newInstance() = CountriesFragment()
    }

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adapter: FlexibleAdapter<AbstractFlexibleItem<*>> =
        FlexibleAdapter(ArrayList<AbstractFlexibleItem<*>>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_countries, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        homeViewModel.countryList.observe(requireActivity(), Observer {
            adapter.updateDataSet(it)
            applySearchIFHas()
        })

        homeViewModel.selectedLocation.observe(requireActivity(), Observer {
            startActivity(CountryDetails.newInstance(requireContext(), it))
            requireActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })
    }

    private fun setupLayout() {
        adapter.isAnimateChangesWithDiffUtil = true
        countryList.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        countryList.layoutManager = layoutManager

        ContextCompat.getDrawable(requireContext(), R.drawable.country_divider)?.let {
            val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
            countryList.addItemDecoration(itemDecoration)
        }
        searchEditText.addTextChangedListener(textWatcher())
    }

    private fun applySearchIFHas() {
        if (searchEditText.text.isNullOrEmpty().not()) {
            searchTermEntered(searchEditText.text.toString())
        }
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

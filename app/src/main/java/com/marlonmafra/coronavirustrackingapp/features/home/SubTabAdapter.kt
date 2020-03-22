package com.marlonmafra.coronavirustrackingapp.features.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SubTabAdapter(
    manager: FragmentManager,
    private val fragmentList: List<Fragment>
) : FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }
}
package org.sopt.sample.presentation

import android.os.Bundle
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.gallery.view.GalleryFragment
import org.sopt.sample.presentation.home.view.HomeFragment
import org.sopt.sample.presentation.search.view.SearchFragment
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.replace

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnItemSelectedListener()
        setOnItemReselectedListener()
        startTargetFragment(R.id.bottom_navigation_home)
    }

    private fun setOnItemSelectedListener() {
        binding.mainBnv.setOnItemSelectedListener { item ->
            startTargetFragment(item.itemId)
            true
        }
    }

    private fun setOnItemReselectedListener() {
        binding.mainBnv.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_home -> (supportFragmentManager.findFragmentById(R.id.main_fcv) as HomeFragment).scrollToTop()
            }
        }
    }

    private fun startTargetFragment(itemId: Int): Any = when (itemId) {
        R.id.bottom_navigation_home -> replace<HomeFragment>(R.id.main_fcv)
        R.id.bottom_navigation_gallery -> replace<GalleryFragment>(R.id.main_fcv)
        R.id.bottom_navigation_search -> replace<SearchFragment>(R.id.main_fcv)
        else -> throw IllegalArgumentException("Not fount error.")
    }
}
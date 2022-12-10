package org.sopt.sample.presentation

import android.os.Bundle
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.home.view.HomeFragment
import org.sopt.sample.presentation.music.view.MusicFragment
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

    private fun startTargetFragment(itemId: Int) = when (itemId) {
        R.id.bottom_navigation_home -> replace<HomeFragment>(R.id.main_fcv)
        R.id.bottom_navigation_music -> replace<MusicFragment>(R.id.main_fcv)
        R.id.bottom_navigation_search -> replace<SearchFragment>(R.id.main_fcv)
        else -> throw IllegalArgumentException("Not found error.")
    }
}
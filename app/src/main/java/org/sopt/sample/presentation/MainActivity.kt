package org.sopt.sample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.gallery.view.GalleryFragment
import org.sopt.sample.presentation.home.view.HomeFragment
import org.sopt.sample.presentation.search.view.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        startTargetFragment(HomeFragment())

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_home -> {
                    startTargetFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navigation_gallery -> {
                    startTargetFragment(GalleryFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navigation_search -> {
                    startTargetFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun startTargetFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fcv, fragment)
            .commitAllowingStateLoss()
    }
}
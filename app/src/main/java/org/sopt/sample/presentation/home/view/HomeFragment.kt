package org.sopt.sample.presentation.home.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.home.adapter.HomeAdapter
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setObservers()
        setAdapter()
    }

    private fun setObservers() {
        viewModel.profileListEvent.observe(
            viewLifecycleOwner, EventObserver { result ->
                if (!result) {
                    viewModel.getProfileList()
                }
            }
        )
    }

    private fun setAdapter() {
        val adapter = HomeAdapter()
        binding.homeRv.adapter = adapter
        viewModel.profileList.observe(
            viewLifecycleOwner
        ) { profileList ->
            adapter.submitList(profileList)
        }
    }

    fun scrollToTop() {
        binding.homeRv.smoothScrollToPosition(0)
    }
}
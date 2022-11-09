package org.sopt.sample.presentation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.util.EventObserver
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.home.adapter.HomeAdapter
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName} error." }
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setObservers()
        setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.signUpEvent.observe(
            viewLifecycleOwner, EventObserver {
                viewModel.getGithubInformations()
            }
        )
    }

    private fun setAdapter() {
        val adapter = HomeAdapter()
        binding.homeRv.adapter = adapter
        viewModel.userInformations.observe(
            viewLifecycleOwner
        ) { userInformations ->
            adapter.submitUserInformations(userInformations)
        }
    }

    fun scrollToTop() {
        binding.homeRv.smoothScrollToPosition(0)
    }
}
package org.sopt.sample.presentation.home.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.sopt.sample.data.model.User
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.presentation.common.EventObserve
import org.sopt.sample.presentation.common.USER
import org.sopt.sample.presentation.common.ViewModelFactory
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

        setUser()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.intent?.getSerializableExtra(USER, User::class.java)
        } else {
            activity?.intent?.getSerializableExtra(USER) as User
        }?.let { user ->
            viewModel.setUser(user)
        }
    }

    private fun setObservers() {
        viewModel.signUpEvent.observe(
            viewLifecycleOwner, EventObserve {
                viewModel.getGithubInformation()
            }
        )

        viewModel.userInformations.observe(
            viewLifecycleOwner
        ) { userInformations ->
            // adapter 연결 코드 작성 예정
        }
    }
}
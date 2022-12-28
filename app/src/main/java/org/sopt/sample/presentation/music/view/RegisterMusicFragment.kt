package org.sopt.sample.presentation.music.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentRegisterMusicBinding
import org.sopt.sample.presentation.music.viewmodel.MusicViewModel
import org.sopt.sample.util.ContentUriRequestBody
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingFragment
import org.sopt.sample.util.extension.hideKeyboard

@AndroidEntryPoint
class RegisterMusicFragment :
    BindingFragment<FragmentRegisterMusicBinding>(R.layout.fragment_register_music) {
    private val viewModel: MusicViewModel by viewModels()
    private val imageLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
        binding.registerMusicIv.load(uri)
        uri?.let {
            viewModel.setImageUriToPart(
                ContentUriRequestBody(
                    requireContext(),
                    it,
                    "image"
                ).toFormData()
            )
        }
    }
    private val permissionLauncher = registerForActivityResult(RequestPermission()) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setPermission()
        setOnClickListener()
        setObservers()
        setNavigation()
    }

    private fun setPermission() {
        permissionLauncher.launch(READ_EXTERNAL_STORAGE)
    }

    private fun setObservers() {
        viewModel.musicEvent.observe(
            viewLifecycleOwner, EventObserver { isSuccess ->
                if (isSuccess) {
                    startMusicFragment()
                }
            }
        )
    }

    private fun setOnClickListener() {
        with(binding) {
            registerMusicIv.setOnClickListener {
                imageLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
            registerMusicCl.setOnClickListener {
                requireContext().hideKeyboard(requireView())
            }
        }
    }

    private fun setNavigation() {
        binding.registerMusicTb.setNavigationOnClickListener {
            startMusicFragment()
        }
    }

    private fun startMusicFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fcv, MusicFragment())
            .commit()
    }
}
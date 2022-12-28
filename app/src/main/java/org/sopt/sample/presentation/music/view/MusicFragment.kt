package org.sopt.sample.presentation.music.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentMusicBinding
import org.sopt.sample.presentation.music.adapter.MusicAdapter
import org.sopt.sample.presentation.music.viewmodel.MusicViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingFragment

@AndroidEntryPoint
class MusicFragment : BindingFragment<FragmentMusicBinding>(R.layout.fragment_music) {
    private val viewModel: MusicViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        getMusicList()
        setAdapter()
        setObservers()
        setOnClickListener()
    }

    private fun getMusicList() {
        viewModel.getMusicList()
    }

    private fun setAdapter() {
        val adapter = MusicAdapter()
        binding.musicRv.adapter = adapter
        viewModel.musicList.observe(
            viewLifecycleOwner
        ) { musicList ->
            adapter.submitList(musicList)
        }
    }

    private fun setObservers() {
        viewModel.musicEvent.observe(
            viewLifecycleOwner, EventObserver { isSuccess ->
                if (!isSuccess) {
                    getMusicList()
                }
            }
        )
    }

    private fun setOnClickListener() {
        binding.musicFab.setOnClickListener {
            startRegisterMusicFragment()
        }
    }

    private fun startRegisterMusicFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fcv, RegisterMusicFragment())
            .commit()
    }
}
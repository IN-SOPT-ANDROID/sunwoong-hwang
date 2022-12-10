package org.sopt.sample.presentation.music.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentMusicBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.music.adapter.MusicAdapter
import org.sopt.sample.presentation.music.viewmodel.MusicViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingFragment

class MusicFragment : BindingFragment<FragmentMusicBinding>(R.layout.fragment_music) {
    private val viewModel: MusicViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        getMusicList()
        setAdapter()
        setObservers()
    }

    private fun getMusicList() {
        viewModel.getMusicList()
    }

    private fun setAdapter() {
        val adapter = MusicAdapter()
        binding.musicRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.musicRv.adapter = adapter
        viewModel.musicList.observe(
            viewLifecycleOwner
        ) { musicList ->
            adapter.submitList(musicList)
        }
    }

    private fun setObservers() {
        with(viewModel) {
            registerMusicListEvent.observe(
                viewLifecycleOwner, EventObserver { isSuccess ->
                    if (!isSuccess) {
                        getMusicList()
                    }
                }
            )
            registerMusicOnClickEvent.observe(
                viewLifecycleOwner, EventObserver { isClick ->
                    if (isClick) {
                        startRegisterMusicFragment()
                    }
                }
            )
        }
    }

    private fun startRegisterMusicFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fcv, RegisterMusicFragment())
            .commit()
    }
}
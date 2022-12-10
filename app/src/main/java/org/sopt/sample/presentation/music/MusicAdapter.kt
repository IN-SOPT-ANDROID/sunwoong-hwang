package org.sopt.sample.presentation.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.model.Music
import org.sopt.sample.databinding.ItemMusicBinding
import org.sopt.sample.util.ItemDiffCallback

class MusicAdapter : ListAdapter<Music, MusicAdapter.MusicViewHolder>(
    ItemDiffCallback<Music>(
        onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return MusicViewHolder(ItemMusicBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class MusicViewHolder(private val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(music: Music) {
            binding.music = music
            binding.executePendingBindings()
        }
    }
}
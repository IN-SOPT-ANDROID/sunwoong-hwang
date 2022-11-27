package org.sopt.sample.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.model.Profile
import org.sopt.sample.databinding.ItemProfileBinding
import org.sopt.sample.util.ItemDiffCallback

class HomeAdapter : ListAdapter<Profile, HomeAdapter.ProfileViewHolder>(ItemDiffCallback<Profile>(
    onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
    onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
)) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return ProfileViewHolder(ItemProfileBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.profile = profile
            binding.executePendingBindings()
        }
    }
}
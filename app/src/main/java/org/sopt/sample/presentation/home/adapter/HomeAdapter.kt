package org.sopt.sample.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.data.model.GithubInformation
import org.sopt.sample.data.model.User
import org.sopt.sample.data.model.UserInformation
import org.sopt.sample.databinding.ItemGithubBinding
import org.sopt.sample.databinding.ItemProfileBinding

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userInformations = mutableListOf<UserInformation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_PROFILE -> HomeUserViewHolder(ItemProfileBinding.inflate(inflater, parent, false))
            else ->  HomeGithubInformationViewHolder(ItemGithubBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeUserViewHolder -> holder.bind(userInformations[position] as User)
            is HomeGithubInformationViewHolder -> holder.bind(userInformations[position] as GithubInformation)
        }
    }

    override fun getItemCount(): Int = userInformations.size

    override fun getItemViewType(position: Int): Int {
        return when (userInformations[position]) {
            is User -> VIEW_TYPE_PROFILE
            else -> VIEW_TYPE_GITHUB
        }
    }

    fun submitUserInformations(userInformations: List<UserInformation>) {
        this.userInformations.addAll(userInformations)
        notifyItemRangeInserted(itemCount, userInformations.size)
    }

    class HomeUserViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    class HomeGithubInformationViewHolder(private val binding: ItemGithubBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubInformation: GithubInformation) {
            binding.githubInformation = githubInformation
            binding.executePendingBindings()
        }
    }

    companion object {
        private const val VIEW_TYPE_PROFILE = 0
        private const val VIEW_TYPE_GITHUB = 1
    }
}
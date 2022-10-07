package org.sopt.sample.presentation.home.view

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.model.User
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        setUser()
        setObservers()
    }

    private fun setUser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getSerializableExtra("user", User::class.java)
        } else {
            intent?.getSerializableExtra("user") as User
        }?.let { user ->
            viewModel.setUser(user)
        }
    }

    private fun setObservers() {
        viewModel.user.observe(
            this
        ) { user ->
            binding.user = user
        }
    }
}
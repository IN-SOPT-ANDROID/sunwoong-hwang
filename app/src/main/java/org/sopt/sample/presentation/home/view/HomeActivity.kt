package org.sopt.sample.presentation.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.model.UserDetail
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

        setUserDetail()
        setObservers()
    }

    private fun setUserDetail() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getSerializableExtra("userDetail", UserDetail::class.java)
        } else {
            intent?.getSerializableExtra("userDetail") as UserDetail
        }?.let { userDetail ->
            viewModel.setUserDetail(userDetail)
        }
    }

    private fun setObservers() {
        viewModel.userDetail.observe(
            this
        ) { userDetail ->
            Log.d("SSS", userDetail.profileUrl)
            binding.userDetail = userDetail
        }
    }
}
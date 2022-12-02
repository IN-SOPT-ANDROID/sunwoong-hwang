package org.sopt.sample.presentation.signup.view

import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.showToast

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        setOnClickListener()
        setNavigation()
    }

    private fun setObservers() {
        viewModel.signUpEvent.observe(
            this, EventObserver { isSuccess ->
                if (isSuccess) {
                    showToast(getString(R.string.success_sign_up))
                    finish()
                } else {
                    showToast(getString(R.string.failure_sign_up))
                }
            }
        )
        viewModel.isValidEmail.observe(
            this
        ) { isValid ->
            binding.signUpIdTil.error =
                if (!isValid) getString(R.string.invalid_email) else null
        }
        viewModel.isValidPassword.observe(
            this
        ) { isValid ->
            binding.signUpPasswordTil.error =
                if (!isValid) getString(R.string.invalid_password) else null
        }
    }

    private fun setOnClickListener() {
        binding.signUpSignUpBtn.setOnClickListener {
            viewModel.signUp()
        }
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }
}
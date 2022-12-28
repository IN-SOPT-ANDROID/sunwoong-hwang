package org.sopt.sample.presentation.auth.view

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.auth.viewmodel.AuthViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.hideKeyboard
import org.sopt.sample.util.extension.showToast

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        setOnClickListener()
        setNavigation()
    }

    private fun setObservers() {
        with(viewModel) {
            authEvent.observe(
                this@SignUpActivity, EventObserver { isSuccess ->
                    if (isSuccess) {
                        showToast(getString(R.string.success_sign_up))
                        finish()
                    } else {
                        showToast(getString(R.string.failure_sign_up))
                    }
                }
            )
            isValidEmail.observe(
                this@SignUpActivity
            ) { isValid ->
                binding.signUpIdTil.error =
                    if (!isValid) getString(R.string.invalid_email) else null
            }
            isValidPassword.observe(
                this@SignUpActivity
            ) { isValid ->
                binding.signUpPasswordTil.error =
                    if (!isValid) getString(R.string.invalid_password) else null
            }
        }
    }

    private fun setOnClickListener() {
        binding.signUpCl.setOnClickListener {
            this.hideKeyboard()
        }
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }
}
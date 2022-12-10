package org.sopt.sample.presentation.auth.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.MainActivity
import org.sopt.sample.presentation.auth.viewmodel.AuthViewModel
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.hideKeyboard
import org.sopt.sample.util.extension.showToast

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: AuthViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        setOnClickListener()
    }

    private fun setObservers() {
        with(viewModel) {
            authEvent.observe(
                this@SignInActivity, EventObserver { isSuccess ->
                    if (isSuccess) {
                        showToast(getString(R.string.success_sign_in))
                        startMainActivity()
                    } else {
                        showToast(getString(R.string.failure_sign_in))
                    }
                }
            )
            isValidEmail.observe(
                this@SignInActivity
            ) { isValid ->
                binding.signInIdTil.error =
                    if (!isValid) getString(R.string.invalid_email) else null
            }
            isValidPassword.observe(
                this@SignInActivity
            ) { isValid ->
                binding.signInPasswordTil.error =
                    if (!isValid) getString(R.string.invalid_password) else null
            }
        }
    }

    private fun setOnClickListener() {
        with(binding) {
            signInSignUpBtn.setOnClickListener {
                startSignUpActivity()
            }
            signInCl.setOnClickListener {
                this@SignInActivity.hideKeyboard()
            }
        }
    }

    private fun startSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
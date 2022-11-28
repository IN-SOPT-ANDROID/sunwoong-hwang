package org.sopt.sample.presentation.signup.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.binding.BindingActivity

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

    private fun getUser(): SignUpRequest {
        with(binding) {
            val email = signUpIdEt.text.toString()
            val password = signUpPasswordEt.text.toString()
            val name = signUpNameEt.text.toString()
            return SignUpRequest(email, password, name)
        }
    }

    private fun setObservers() {
        viewModel.signUpEvent.observe(
            this, EventObserver { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, R.string.success_sign_up, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, R.string.failure_sign_up, Toast.LENGTH_SHORT).show()
                }
            }
        )
        viewModel.isValidEmail.observe(
            this
        ) { isValid ->
            binding.signUpIdTil.error =
                if (!isValid) resources.getString(R.string.invalid_email) else null
        }
        viewModel.isValidPassword.observe(
            this
        ) { isValid ->
            binding.signUpPasswordTil.error =
                if (!isValid) resources.getString(R.string.invalid_password) else null
        }
    }

    private fun setOnClickListener() {
        binding.signUpSignUpBtn.setOnClickListener {
            viewModel.signUp(getUser())
        }
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }
}
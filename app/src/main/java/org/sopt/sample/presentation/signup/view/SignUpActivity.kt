package org.sopt.sample.presentation.signup.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import org.sopt.sample.R
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel
import org.sopt.sample.util.EMAIL
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.NAME
import org.sopt.sample.util.PASSWORD
import org.sopt.sample.util.binding.BindingActivity

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        setObservers()
        setOnClickListener()
        setAddTextChangedListener()
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
        viewModel.isPromising.observe(
            this, EventObserver { isSuccess ->
                binding.signUpSignUpBtn.isEnabled = isSuccess
            }
        )
    }

    private fun setOnClickListener() {
        binding.signUpSignUpBtn.setOnClickListener {
            viewModel.signUp(getUser())
        }
    }

    private fun setAddTextChangedListener() {
        with(binding) {
            signUpIdEt.addTextChangedListener {
                viewModel.setUserStatus(EMAIL, !signUpIdEt.text.isNullOrEmpty())
            }
            signUpPasswordEt.addTextChangedListener {
                viewModel.setUserStatus(PASSWORD, !signUpPasswordEt.text.isNullOrEmpty())
            }
            signUpNameEt.addTextChangedListener {
                viewModel.setUserStatus(NAME, !signUpNameEt.text.isNullOrEmpty())
            }
        }
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }
}
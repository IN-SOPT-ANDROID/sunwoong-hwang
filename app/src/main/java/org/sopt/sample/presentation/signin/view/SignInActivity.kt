package org.sopt.sample.presentation.signin.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import org.sopt.sample.R
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.MainActivity
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.view.SignUpActivity
import org.sopt.sample.util.EMAIL
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.PASSWORD
import org.sopt.sample.util.binding.BindingActivity

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: SignInViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        setObservers()
        setOnClickListener()
        setAddTextChangedListener()
    }

    private fun getUser(): SignInRequest {
        with(binding) {
            val email = signInIdEt.text.toString()
            val password = signInPasswordEt.text.toString()
            return SignInRequest(email, password)
        }
    }

    private fun setObservers() {
        viewModel.signInEvent.observe(
            this, EventObserver { isPossible ->
                if (isPossible) {
                    Toast.makeText(this, R.string.success_sign_in, Toast.LENGTH_SHORT).show()
                    startMainActivity()
                } else {
                    Toast.makeText(this, R.string.failure_sign_in, Toast.LENGTH_SHORT).show()
                }
            }
        )
        viewModel.isPromising.observe(
            this, EventObserver { isSuccess ->
                binding.signInSignInBtn.isEnabled = isSuccess
            }
        )
    }

    private fun setOnClickListener() {
        with(binding) {
            signInSignInBtn.setOnClickListener {
                viewModel.signIn(getUser())
            }
            signInSignUpBtn.setOnClickListener {
                startSignUpActivity()
            }
        }
    }

    private fun setAddTextChangedListener() {
        with(binding) {
            signInIdEt.addTextChangedListener {
                viewModel.setUserStatus(EMAIL, !signInIdEt.text.isNullOrEmpty())
            }
            signInPasswordEt.addTextChangedListener {
                viewModel.setUserStatus(PASSWORD, !signInPasswordEt.text.isNullOrEmpty())
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
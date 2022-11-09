package org.sopt.sample.presentation.signin.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                if (isSuccess) {
                    binding.signInSignInBtn.isEnabled = true
                    binding.signInSignInBtn.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                } else {
                    binding.signInSignInBtn.isEnabled = false
                    binding.signInSignInBtn.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this,
                                R.color.gray
                            )
                        )
                }
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
                if (signInIdEt.text.isNullOrEmpty()) {
                    viewModel.setUserStatus(EMAIL, false)
                } else {
                    viewModel.setUserStatus(EMAIL, true)
                }
            }
            signInPasswordEt.addTextChangedListener {
                if (signInPasswordEt.text.isNullOrEmpty()) {
                    viewModel.setUserStatus(PASSWORD, false)
                } else {
                    viewModel.setUserStatus(PASSWORD, true)
                }
            }
        }
    }

    private fun startSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
package org.sopt.sample.presentation.signup.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import org.sopt.sample.R
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signin.view.SignInActivity
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel
import org.sopt.sample.util.EMAIL
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.NAME
import org.sopt.sample.util.PASSWORD

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    startSignInActivity()
                } else {
                    Toast.makeText(this, R.string.failure_sign_up, Toast.LENGTH_SHORT).show()
                }
            }
        )
        viewModel.isPromising.observe(
            this, EventObserver { isSuccess ->
                if (isSuccess) {
                    binding.signUpSignUpBtn.isEnabled = true
                    binding.signUpSignUpBtn.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                } else {
                    binding.signUpSignUpBtn.isEnabled = false
                    binding.signUpSignUpBtn.backgroundTintList =
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
        binding.signUpSignUpBtn.setOnClickListener {
            viewModel.signUp(getUser())
        }
    }

    private fun setAddTextChangedListener() {
        with(binding) {
            signUpIdEt.addTextChangedListener {
                if (signUpIdEt.text.isNullOrEmpty()) {
                    viewModel.setUserStatus(EMAIL, false)
                } else {
                    viewModel.setUserStatus(EMAIL, true)
                }
            }
            signUpPasswordEt.addTextChangedListener {
                if (signUpPasswordEt.text.isNullOrEmpty()) {
                    viewModel.setUserStatus(PASSWORD, false)
                } else {
                    viewModel.setUserStatus(PASSWORD, true)
                }
            }
            signUpNameEt.addTextChangedListener {
                if (signUpNameEt.text.isNullOrEmpty()) {
                    viewModel.setUserStatus(NAME, false)
                } else {
                    viewModel.setUserStatus(NAME, true)
                }
            }
        }
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }

    private fun startSignInActivity() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
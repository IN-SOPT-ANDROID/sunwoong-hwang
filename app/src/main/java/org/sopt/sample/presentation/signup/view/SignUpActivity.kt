package org.sopt.sample.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.model.User
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.common.EventObserve
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.signin.view.SignInActivity
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        setOnClickListener()
        setObservers()
        setNavigation()
    }

    private fun getUser(): User {
        with(binding) {
            val id = signUpIdEt.text.toString()
            val password = signUpPasswordEt.text.toString()
            val mbti = signUpMbtiEt.text.toString()
            val part = signUpPartEt.text.toString()
            val nickname = signUpNicknameEt.text.toString()
            return User(id, password, mbti, part, nickname)
        }
    }

    private fun setOnClickListener() {
        binding.signUpSignUpBtn.setOnClickListener {
            viewModel.signUp(getUser())
        }
    }

    private fun setObservers() {
        viewModel.signUpEvent.observe(
            this, EventObserve { isPossible ->
                if (isPossible) {
                    startSignInActivity(viewModel.getUser()!!)
                } else {
                    Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setNavigation() {
        binding.signUpTb.setNavigationOnClickListener {
            finish()
        }
    }

    private fun startSignInActivity(user: User) {
        val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra("user", user)
        setResult(RESULT_OK, intent)
        finish()
    }
}
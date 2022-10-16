package org.sopt.sample.presentation.signin.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.data.model.User
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.MainActivity
import org.sopt.sample.presentation.common.*
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.view.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        setOnClickListener()
        setObservers()
        setActivityResultLauncher()
    }

    private fun setOnClickListener() {
        with(binding) {
            signInSignInBtn.setOnClickListener {
                viewModel.signIn(signInIdEt.text.toString(), signInPasswordEt.text.toString())
            }
            signInSignUpBtn.setOnClickListener {
                startSignUpActivity()
            }
        }
    }

    private fun setObservers() {
        viewModel.signInEvent.observe(
            this, EventObserver { isPossible ->
                if (isPossible) {
                    Toast.makeText(this, R.string.success_sign_in, Toast.LENGTH_SHORT).show()
                    setSharedPreferenceToUser(viewModel.getUser()!!)
                    startMainActivity()
                } else {
                    Toast.makeText(this, R.string.failure_sign_in, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setActivityResultLauncher() {
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val user: User? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getSerializableExtra(USER, User::class.java)
                    } else {
                        result.data?.getSerializableExtra(USER) as User?
                    }
                    Snackbar.make(binding.root, R.string.success_sign_up, Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.setUser(user!!)
                }
            }
    }

    private fun startSignUpActivity() {
        launcher.launch(Intent(this, SignUpActivity::class.java))
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(USER, viewModel.getUser())
        startActivity(intent)
        finish()
    }

    private fun setSharedPreferenceToUser(user: User) {
        val sharedPreferences = getSharedPreferences(AUTH, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        with(editor) {
            putString(ID, user.id)
            putString(PASSWORD, user.password)
            putString(MBTI, user.mbti)
            putString(PART, user.part)
            putString(NICKNAME, user.nickname)
            putString(PROFILE_URL, user.profileUrl)
            apply()
        }
    }
}
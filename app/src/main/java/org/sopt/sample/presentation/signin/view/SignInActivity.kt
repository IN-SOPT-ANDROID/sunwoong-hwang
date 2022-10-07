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
import org.sopt.sample.data.model.User
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.common.EventObserve
import org.sopt.sample.presentation.common.ViewModelFactory
import org.sopt.sample.presentation.home.view.HomeActivity
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.view.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels { ViewModelFactory() }
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
            this, EventObserve { isPossible ->
                if (isPossible) {
                    Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    startHomeActivity()
                } else {
                    Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setActivityResultLauncher() {
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val user: User? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getSerializableExtra("user", User::class.java)
                    } else {
                        result.data?.getSerializableExtra("user") as User?
                    }
                    Snackbar.make(binding.root, "SOPT에 오신걸 환영합니다!", Snackbar.LENGTH_SHORT).show()
                    viewModel.setUser(user!!)
                }
            }
    }

    private fun startSignUpActivity() {
        launcher.launch(Intent(this, SignUpActivity::class.java))
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("userDetail", viewModel.getUserDetail())
        startActivity(intent)
        finish()
    }
}

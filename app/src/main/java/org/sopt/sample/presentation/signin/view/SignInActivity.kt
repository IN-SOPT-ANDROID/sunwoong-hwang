package org.sopt.sample.presentation.signin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

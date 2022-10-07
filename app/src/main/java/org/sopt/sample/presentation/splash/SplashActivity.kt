package org.sopt.sample.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.model.User
import org.sopt.sample.presentation.home.view.HomeActivity
import org.sopt.sample.presentation.signin.view.SignInActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isSignIn()
    }

    private fun isSignIn() {
        val user: User? = getUserFromSharedPreference()
        if (user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun getUserFromSharedPreference(): User? {
        val sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE)
        with(sharedPreferences) {
            val id = getString("id", null)
            if (!id.isNullOrEmpty()) {
                return User(
                    getString("id", null) ?: "",
                    getString("password", null) ?: "",
                    getString("mbti", null) ?: "",
                    getString("part", null) ?: "",
                    getString("nickname", null) ?: "",
                    getString("profileUrl", null) ?: ""
                )
            }
            return null
        }
    }
}
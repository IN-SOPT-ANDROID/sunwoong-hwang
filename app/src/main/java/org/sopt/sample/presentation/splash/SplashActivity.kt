package org.sopt.sample.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.R
import org.sopt.sample.data.model.User
import org.sopt.sample.presentation.common.*
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
            Toast.makeText(this, R.string.success_sign_in, Toast.LENGTH_SHORT).show()
            intent.putExtra(USER, user)
            startActivity(intent)
            finish()
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun getUserFromSharedPreference(): User? {
        val sharedPreferences = getSharedPreferences(AUTH, MODE_PRIVATE)
        with(sharedPreferences) {
            val id = getString(ID, null)
            if (!id.isNullOrEmpty()) {
                return User(
                    getString(ID, null) ?: "",
                    getString(PASSWORD, null) ?: "",
                    getString(MBTI, null) ?: "",
                    getString(PART, null) ?: "",
                    getString(NICKNAME, null) ?: "",
                    getString(PROFILE_URL, null) ?: ""
                )
            }
            return null
        }
    }
}
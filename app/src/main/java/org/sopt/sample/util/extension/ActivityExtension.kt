package org.sopt.sample.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

inline fun <reified T : Fragment> AppCompatActivity.replace(@IdRes frameId: Int) {
    supportFragmentManager.commit {
        replace<T>(frameId)
        setReorderingAllowed(true)
    }
}
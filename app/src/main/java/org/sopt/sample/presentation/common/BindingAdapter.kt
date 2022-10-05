package org.sopt.sample.presentation.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("circleImageUrl")
fun circleImageUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .circleCrop()
            .into(view)
    }
}
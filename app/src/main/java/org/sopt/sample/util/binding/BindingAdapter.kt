package org.sopt.sample.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import org.sopt.sample.util.GlideApp

@BindingAdapter("imageUrl")
fun imageUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .into(view)
    }
}
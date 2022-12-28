package org.sopt.sample.util.binding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
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

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isVisible = isVisible
}
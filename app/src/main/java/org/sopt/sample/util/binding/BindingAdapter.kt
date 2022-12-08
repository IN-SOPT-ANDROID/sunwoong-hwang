package org.sopt.sample.util.binding

import android.view.View
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

@BindingAdapter("visibility")
fun setVisibility(view: View, isVisible: Boolean?) {
    if (isVisible == null) return
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}
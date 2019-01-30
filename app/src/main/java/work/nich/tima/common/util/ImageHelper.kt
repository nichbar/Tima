package work.nich.tima.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import work.nich.tima.common.network.GlideApp

object ImageHelper {
    fun loadImage(context: Context, id: Int, imageView: ImageView) {
        GlideApp.with(context)
            .load(id)
            .into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context)
            .load(url)
            .into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView, placeholder: Int) {
        GlideApp.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView, placeholder: Drawable) {
        GlideApp.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)
    }


    fun loadImageWithCircleCrop(context: Context, url: String, imageView: ImageView) {
        GlideApp.with(context)
            .load(url)
            .circleCrop()
            .into(imageView)
    }

    fun loadRoundCornerImage(context: Context, url: String, imageView: ImageView, Radius: Int) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(Radius))
        GlideApp.with(context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}
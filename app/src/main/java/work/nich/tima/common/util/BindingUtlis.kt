package work.nich.tima.common.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import work.nich.tima.Tima

@BindingAdapter("image")
fun setImage(view: ImageView, imageUrl: String) {
    ImageHelper.loadImage(view.context, imageUrl, view)
}

@BindingAdapter("image", "placeHolder")
fun setImage(view: ImageView, imageUrl: String, placeHolder: Drawable) {
    ImageHelper.loadImage(view.context, imageUrl, view, placeHolder)
}

@BindingAdapter("roundCornerImage")
fun setRoundCornerImage(view: ImageView, imageUrl: String) {
    ImageHelper.loadRoundCornerImage(view.context, imageUrl, view, DisplayUtils.dp2px(Tima.app, 9f))
}

@BindingAdapter("visible")
fun setVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("visibleIfExist")
fun setVisibility(view: View, o: Any?) {
    if (o != null) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("visibleIfNotExist")
fun adjustVisibility(view: View, o: Any?) {
    if (o == null) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
package io.mapomi.android.ui.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.mapomi.android.R
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.system.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("isSelect")
fun setIsSelect(view: View, status : Boolean){
    view.isSelected = status
}

@BindingAdapter("onActionSearch")
fun onActionSend(view: EditText, listener: View.OnClickListener)
{
    view.setOnEditorActionListener { v, actionId, event ->

        when(actionId){

            EditorInfo.IME_ACTION_SEARCH -> {

                listener.onClick(v)

                val im =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()

                return@setOnEditorActionListener true
            }
        }

        false
    }
}

@BindingAdapter("requestFocus", "soft")
fun requestFocus(view: EditText, focus: Boolean, soft: SoftKeyModel) {
    if (focus) {
        view.requestFocus()
        soft.imm.showSoftInput(view, 0)
    }
}


@BindingAdapter("app:tint")
fun imageTint(view: ImageView, color: Int) {
    view.imageTintList = ColorStateList.valueOf(color)
}


@BindingAdapter("marginVertical", "marginHorizon")
fun setRecyclerViewItemMargin(view: RecyclerView, vertical: Float = 0f, horizontal: Float = 0f) {
    view.addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = horizontal.dp.toInt()
                outRect.top = vertical.dp.toInt()
                outRect.right = horizontal.dp.toInt()
                outRect.bottom = vertical.dp.toInt()

            }
        }
    )
}


@BindingAdapter("app:bottomReached")
fun recyclerViewBottomReached(view: RecyclerView, listener: View.OnClickListener) {
    view.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (view.computeVerticalScrollOffset() >= view.computeVerticalScrollRange() - view.computeVerticalScrollExtent())
                    listener.onClick(view)
            }
        }
    )
}

@BindingAdapter("remoteImgUrl","imgRadius")
fun setImg(view: ImageView, url : String?, radius : Float)
{
    if (url.isNullOrEmpty()){
        view.setImageDrawable(
            AppCompatResources.getDrawable(
                view.context,
                R.drawable.ic_default_picture
            )
        )
        return
    }

    CoroutineScope(Dispatchers.IO).launch {
        Glide.with(view).asBitmap().load(GlideUrl(url))
            .transform(CenterCrop(), RoundedCorners(radius.dp.toInt())).apply {
                CoroutineScope(Dispatchers.Main).launch {
                    into(view)
                }
            }
    }
}
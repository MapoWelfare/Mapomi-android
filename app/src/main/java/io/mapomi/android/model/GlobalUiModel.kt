package io.mapomi.android.model

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.mapomi.android.ui.auth.AuthActivity
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.utils.FileUtil
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalUiModel @Inject constructor() {
    private var _activity : BaseActivity<*>? = null
    private var _context : Context? = null

    fun registerActivity(activity: BaseActivity<*>, context: Context){
        _activity = activity
        _context = context
    }

    fun getActivity() : AppCompatActivity? {
        return _activity
    }

    fun showToast(msg : String) {
        Toast.makeText(_context,msg,Toast.LENGTH_SHORT).show()
    }

    fun goToLogin(){
        _activity?.startActivity(Intent(_activity, AuthActivity::class.java))
    }

    /*******************************************
     **** 이미지
     ******************************************/

    fun loadImage(callback : (Uri) -> Unit)
    {
        _activity?.let { activity ->
            FileUtil.pickFromGallery(activity,
                {
                callback(it[0])
                },
                false)
        }
    }

    fun getImgRealName(uri: Uri) : String
    {
        return FileUtil.getRealName(_context!!,uri)
    }

    fun convertImgToUpload(uri: Uri) : MultipartBody.Part
    {
        val imgBitmap : Bitmap = FileUtil.getBitmapFile(_context!!,uri)
        return FileUtil.getMultipart(_context!!,"multipartFile",imgBitmap)
    }


    /*******************************************
     **** 다이얼로그
     ******************************************/

    private var dialog : Dialog? = null

    private fun isDialogOn() : Boolean {
        dialog?.let {
            if (it.isShowing) return true
        }
        return false
    }


}
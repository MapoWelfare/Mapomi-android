package io.mapomi.android.model

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.mapomi.android.ui.base.BaseActivity
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
}
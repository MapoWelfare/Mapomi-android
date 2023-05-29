package io.mapomi.android.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.mapomi.android.model.GlobalSystemModel
import io.mapomi.android.model.GlobalUiModel
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.system.LogDebug
import io.mapomi.android.system.LogInfo
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding>(private val layoutId : Int) : AppCompatActivity() {

    private lateinit var bind : T

    @Inject lateinit var softKey : SoftKeyModel
    @Inject lateinit var uiModel : GlobalUiModel
    @Inject lateinit var systemModel: GlobalSystemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this,layoutId)
        bind.lifecycleOwner = this
        registerModels()
        LogDebug(javaClass.name, "ACTIVITY CREATED")
        onActivityCreate()
    }

    fun useBind(_bind : T.()->Unit)
    {
        _bind(bind)
    }

    private fun registerModels()
    {
        softKey.registerActivity(this)
        uiModel.registerActivity(this,this)
        systemModel.registerActivity(this)
    }

    override fun onResume() {
        super.onResume()
        registerModels()
    }

    private lateinit var launcherResponse : (Intent?)->Unit

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        LogInfo(javaClass.name, "[RESUULT LAUNCHER] resultCode : ${it.resultCode}, data : ${it.data}")
        if (it.resultCode == Activity.RESULT_OK) {
            launcherResponse(it.data)
        }
    }

    fun gotoActivityForResult(intent: Intent, onResponse : (Intent?)->Unit)
    {
        launcherResponse = onResponse
        resultLauncher.launch(intent)
    }

    abstract fun onActivityCreate()
}
package io.mapomi.android.ui.main.post.oneClick

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.mapomi.android.ui.base.BottomDialog
import javax.inject.Inject

@ActivityRetainedScoped
class OneClickConnect @Inject constructor() {

    private var _dialog : BottomDialog? = null

    fun registerContext(dialog: BottomDialog)
    {
        _dialog = dialog
    }

    fun dismissDialog()
    {
        _dialog?.dismiss()
    }
}
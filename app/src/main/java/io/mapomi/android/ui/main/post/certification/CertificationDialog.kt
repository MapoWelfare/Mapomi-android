package io.mapomi.android.ui.main.post.certification

import android.content.DialogInterface
import androidx.fragment.app.activityViewModels
import io.mapomi.android.R
import io.mapomi.android.databinding.DialogCertificationBinding
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.base.BaseDialogFragment

class CertificationDialog : BaseDialogFragment<DialogCertificationBinding>(R.layout.dialog_certification) {

    val viewModel : CertificationViewModel by activityViewModels()

    override fun initView(bind: DialogCertificationBinding) {
        bind.vm = viewModel
    }

    override fun onDismiss(dialog: DialogInterface) {
        LogInfo(javaClass.name,"다이얼로그 DISMISS")
        super.onDismiss(dialog)
    }
}
package io.mapomi.android.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.mapomi.android.R

abstract class BottomDialog : BottomSheetDialogFragment() {

    lateinit var behavior: BottomSheetBehavior<View>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createDialogView(inflater, container)
    }

    abstract fun createDialogView(inflater: LayoutInflater, container: ViewGroup?) : View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBehavior()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        onCreateDialog()
    }

    abstract fun onCreateDialog()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
        bottomSheetDialog.setOnShowListener { dialog ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)!!

            val bottomSheetBehavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    isFitToContents = true
                    expandedOffset = 1000
                }

            isCancelable = true
        }
        return bottomSheetDialog
    }

    private fun setBehavior() {
        BottomSheetBehavior.from<View>(dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!)
            .apply {
                behavior = this
                isDraggable = true
                state = BottomSheetBehavior.STATE_EXPANDED
            }
    }

}
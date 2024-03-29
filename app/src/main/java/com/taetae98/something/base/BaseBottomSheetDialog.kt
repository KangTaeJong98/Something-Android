package com.taetae98.something.base

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {
    protected fun setLayout(width: Int = ViewGroup.LayoutParams.WRAP_CONTENT, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog?.window?.setLayout(width, height)
    }

    protected fun setBackground(color: Int) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(color))
    }

    protected fun setAnimation(resId: Int) {
        dialog?.window?.attributes?.windowAnimations = resId
    }
}
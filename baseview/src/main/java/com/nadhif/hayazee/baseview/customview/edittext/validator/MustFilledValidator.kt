package com.nadhif.hayazee.baseview.customview.edittext.validator

import com.google.android.material.textfield.TextInputLayout
import com.nadhif.hayazee.baseview.customview.edittext.CustomTextInputLayout

class MustFilledValidator : CustomTextInputLayout.Validator {
    override fun validate(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.editText?.text.toString().trim().isNotEmpty()
    }
}
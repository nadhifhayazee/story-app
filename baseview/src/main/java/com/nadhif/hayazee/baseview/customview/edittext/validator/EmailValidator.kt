package com.nadhif.hayazee.baseview.customview.edittext.validator

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import com.nadhif.hayazee.baseview.customview.edittext.CustomTextInputLayout

class EmailValidator : CustomTextInputLayout.Validator {
    override fun validate(textInputLayout: TextInputLayout): Boolean {
        val value = textInputLayout.editText?.text.toString()
        return if (value.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                val char = value.split("@")[0]
                char.length >= 3
            } else {
                false
                // return false
            }
        } else {
            false
        }
    }
}
package com.nadhif.hayazee.baseview.customview.edittext

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData

class FormValidator(private val listTextInputLayout: List<CustomTextInputLayout>) {

    val isFormValidated = MutableLiveData(false)

    init {
        listTextInputLayout.forEach {
            it.editText?.doAfterTextChanged {
                isFormValidated.value = getIsFormValidate()
            }
        }
    }

    private fun getIsFormValidate(): Boolean {
        for (i in 0..listTextInputLayout.lastIndex) {
            if (listTextInputLayout[i].isValidated.value == false) return false
        }

        return true
    }


}
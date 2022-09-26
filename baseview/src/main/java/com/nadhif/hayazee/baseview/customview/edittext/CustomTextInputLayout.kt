package com.nadhif.hayazee.baseview.customview.edittext

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.nadhif.hayazee.baseview.R

class CustomTextInputLayout(
    context: Context,
    attrs: AttributeSet
) : TextInputLayout(context, attrs), TextWatcher {
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : this(context, attrs)

    var isValidated: MutableLiveData<Boolean> = MutableLiveData(false)
    private var _attributes: TypedArray

    private var errorMessage: String? = null

    init {
        _attributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout)
        errorMessage = _attributes.getString(R.styleable.CustomTextInputLayout_errorMessage)

    }

    private var _validator: Validator? = null
    val validator get() = _validator

    fun setValidator(validator: Validator) {
        _validator = validator
        super.getEditText()?.addTextChangedListener(this)
    }


    fun validate(): Boolean {
        if (_validator == null) {
            isValidated.value = true
            return true
        }
        isValidated.value = _validator?.validate(this)
        return isValidated.value == true
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (validate()) {
            error = null
            isErrorEnabled = false
        } else {
            isErrorEnabled = true
            error = errorMessage
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    interface Validator {
        fun validate(textInputLayout: TextInputLayout): Boolean
    }
}
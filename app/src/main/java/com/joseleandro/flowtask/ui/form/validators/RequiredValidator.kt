package com.joseleandro.flowtask.ui.form.validators

import com.joseleandro.flowtask.R

class RequiredValidator<T>(
    override val messageError: Int = R.string.campo_obrigatorio
) : Validator<T?> {

    override fun validate(value: T?): Boolean {
        return value != null
    }
}
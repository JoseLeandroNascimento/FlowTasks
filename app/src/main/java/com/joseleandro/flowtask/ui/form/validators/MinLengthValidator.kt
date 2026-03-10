package com.joseleandro.flowtask.ui.form.validators

import androidx.annotation.StringRes

class MinLengthValidator(
    private val min: Int,
    @get:StringRes override val messageError: Int
) : Validator<String> {

    override fun validate(value: String): Boolean {
        return value.length >= min
    }
}
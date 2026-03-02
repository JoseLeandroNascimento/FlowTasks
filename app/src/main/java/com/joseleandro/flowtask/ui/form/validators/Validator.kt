package com.joseleandro.flowtask.ui.form.validators

import androidx.annotation.StringRes

interface Validator<T> {

    @get:StringRes
    val messageError: Int

    fun validate(value: T): Boolean

}
package com.joseleandro.flowtask.ui.form.validators

interface Validator<T> {

    val messageError: String

    fun validate(value: T): Boolean

}
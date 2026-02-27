package com.joseleandro.flowtask.ui.form.validators

class RequiredValidator<T>(
    override val messageError: String = "Campo obrigatório"
) : Validator<T?> {

    override fun validate(value: T?): Boolean {
        return value != null
    }
}
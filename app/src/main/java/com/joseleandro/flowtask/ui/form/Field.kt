package com.joseleandro.flowtask.ui.form

import com.joseleandro.flowtask.ui.form.validators.Validator


data class Field<T>(
    val value: T,
    val error: String? = null,
    val validators: List<Validator<T>> = emptyList()
) {

    val isValid: Boolean
        get() = error == null


    fun updateValue(value: T): Field<T> {
        return this.copy(
            value = value,
            error = validate()
        )
    }

    private fun validate(): String? {
        return this.validators.find { validators ->
            !validators.validate(value)
        }?.messageError
    }

}

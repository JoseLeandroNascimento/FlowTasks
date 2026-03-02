package com.joseleandro.flowtask.ui.form

import androidx.annotation.StringRes
import com.joseleandro.flowtask.ui.form.validators.Validator


data class Field<T>(
    val value: T,
    @get:StringRes val error: Int? = null,
    val validators: List<Validator<T>> = emptyList()
) {

    val isValid: Boolean
        get() = error == null


    fun updateValue(value: T): Field<T> {

        val field = this.copy(
            value = value,
        )
        return field.copy(
            error = field.validate()
        )
    }

    fun validate(): Int? {
        return this.validators.find { validators ->
            !validators.validate(value)
        }?.messageError
    }

}

package com.joseleandro.flowtask.ui.form

interface FormState {

    val isValid: Boolean

    fun validate(): FormState

}
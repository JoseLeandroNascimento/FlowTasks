package com.joseleandro.flowtask.ui.form

import kotlin.reflect.full.memberProperties

abstract class FormState {

    val isValid: Boolean
        get() = this::class.memberProperties
            .filter { it.name != "isValid" }
            .map { prop -> prop.getter.call(this) }
            .filterIsInstance<Field<*>>()
            .all { field -> field.isValid }

}
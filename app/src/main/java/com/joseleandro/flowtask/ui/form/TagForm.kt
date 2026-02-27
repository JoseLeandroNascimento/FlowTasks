package com.joseleandro.flowtask.ui.form

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.ui.form.validators.RequiredValidator
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet

data class TagForm(
    val id: Field<Int?> = Field(null),
    val name: Field<String?> = Field(
        value = null,
        validators = listOf(
            RequiredValidator()
        )
    ),
    val color: Field<Color?> = Field(
        value = ColorPickerPallet.colorDefault,
        validators = listOf(
            RequiredValidator()
        )
    )
) : FormState()

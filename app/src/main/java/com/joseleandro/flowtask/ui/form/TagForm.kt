package com.joseleandro.flowtask.ui.form

import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.ui.form.validators.NotBlankValidator
import com.joseleandro.flowtask.ui.theme.ColorPickerPallet

data class TagForm(
    val id: Field<Int?> = Field(null),
    val name: Field<String> = Field(
        value = "",
        validators = listOf(NotBlankValidator())
    ),
    val color: Field<Color> = Field(
        value = ColorPickerPallet.colorDefault,
    )
) : FormState {

    override val isValid: Boolean
        get() {
            return name.isValid && color.isValid
        }

    override fun validate(): TagForm {
        return copy(
            name = name.updateValue(name.value),
            color = color.updateValue(color.value)
        )
    }

}

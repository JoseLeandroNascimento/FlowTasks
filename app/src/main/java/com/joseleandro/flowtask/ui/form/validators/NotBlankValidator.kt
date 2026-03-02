package com.joseleandro.flowtask.ui.form.validators

import com.joseleandro.flowtask.R

class NotBlankValidator(
    override val messageError: Int = R.string.o_campo_n_o_pode_estar_em_branco
) : Validator<String> {

    override fun validate(value: String): Boolean {
        return value.trim().isNotEmpty()
    }
}
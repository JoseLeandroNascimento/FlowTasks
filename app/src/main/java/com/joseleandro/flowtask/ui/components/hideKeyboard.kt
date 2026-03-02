package com.joseleandro.flowtask.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun Modifier.hideKeyboard(): Modifier{

    val focusManager = LocalFocusManager.current

    return then(
        Modifier.clickable(
            indication = null,
            interactionSource = null
        ){
            focusManager.clearFocus()
        }
    )
}
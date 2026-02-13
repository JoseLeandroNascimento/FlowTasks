package com.joseleandro.flowtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.joseleandro.flowtask.ui.appRoot.AppNavigation
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowTaskTheme(
                dynamicColor = false
            ) {
                AppNavigation()
            }
        }
    }
}
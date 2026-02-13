package com.joseleandro.flowtask.core.di

import com.joseleandro.flowtask.ui.appRoot.NavigationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        NavigationViewModel()
    }
}
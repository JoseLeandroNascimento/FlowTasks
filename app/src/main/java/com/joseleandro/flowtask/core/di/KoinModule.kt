package com.joseleandro.flowtask.core.di

import androidx.room.Room
import com.joseleandro.flowtask.data.local.database.FLOW_TASK_DATABASE_NAME
import com.joseleandro.flowtask.data.local.database.FlowTaskDatabase
import com.joseleandro.flowtask.data.local.database.TaskDao
import com.joseleandro.flowtask.ui.viewmodel.CreateTaskViewModel
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {

    single<FlowTaskDatabase> {
        Room.databaseBuilder(
            context = androidApplication(),
            FlowTaskDatabase::class.java,
            FLOW_TASK_DATABASE_NAME
        ).build()
    }

    single<TaskDao> {
        get<FlowTaskDatabase>().taskDao()
    }
}

val uiModule = module {

    viewModelOf(::NavigationViewModel)
    viewModelOf(::CreateTaskViewModel)
}
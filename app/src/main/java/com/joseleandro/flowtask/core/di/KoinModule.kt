package com.joseleandro.flowtask.core.di

import androidx.room.Room
import com.joseleandro.flowtask.data.datasource.TagDataSource
import com.joseleandro.flowtask.data.datasource.TagDataSourceImpl
import com.joseleandro.flowtask.data.local.database.FLOW_TASK_DATABASE_NAME
import com.joseleandro.flowtask.data.local.database.FlowTaskDatabase
import com.joseleandro.flowtask.data.local.database.TagDao
import com.joseleandro.flowtask.data.local.database.TaskDao
import com.joseleandro.flowtask.data.repository.TagRepositoryImpl
import com.joseleandro.flowtask.domain.repositoty.TagRepository
import com.joseleandro.flowtask.domain.usecase.DeleteTagByIdUseCase
import com.joseleandro.flowtask.domain.usecase.InsertTagUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.ui.viewmodel.CreateTagViewModel
import com.joseleandro.flowtask.ui.viewmodel.CreateTaskViewModel
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import com.joseleandro.flowtask.ui.viewmodel.TagsViewModel
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

    single<TagDao> {
        get<FlowTaskDatabase>().tagDao()
    }

    single<TagDataSource> {
        TagDataSourceImpl(
            tagDao = get()
        )
    }

    single<TagRepository> {
        TagRepositoryImpl(
            tagDataSource = get()
        )
    }

}

val domainModule = module {

    factory<InsertTagUseCase> {
        InsertTagUseCase(
            tagRepository = get()
        )
    }

    factory<TagsAllUseCase> {
        TagsAllUseCase(
            tagRepository = get()
        )
    }

    factory<DeleteTagByIdUseCase> {
        DeleteTagByIdUseCase(
            tagRepository = get()
        )
    }
}

val uiModule = module {

    viewModelOf(::NavigationViewModel)
    viewModelOf(::CreateTaskViewModel)
    viewModelOf(::TagsViewModel)
    viewModelOf(::CreateTagViewModel)
}
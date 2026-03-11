package com.joseleandro.flowtask.core.di

import androidx.room.Room
import com.joseleandro.flowtask.data.datasource.TagDataSource
import com.joseleandro.flowtask.data.datasource.TagDataSourceImpl
import com.joseleandro.flowtask.data.datasource.TaskDataSource
import com.joseleandro.flowtask.data.datasource.TaskDataSourceImpl
import com.joseleandro.flowtask.data.local.database.FLOW_TASK_DATABASE_NAME
import com.joseleandro.flowtask.data.local.database.FlowTaskDatabase
import com.joseleandro.flowtask.data.local.database.TagDao
import com.joseleandro.flowtask.data.local.database.TaskDao
import com.joseleandro.flowtask.data.local.datastore.TaskPreferencesDataStore
import com.joseleandro.flowtask.data.repository.TagRepositoryImpl
import com.joseleandro.flowtask.data.repository.TaskRepositoryImpl
import com.joseleandro.flowtask.domain.repositoty.TagRepository
import com.joseleandro.flowtask.domain.repositoty.TaskRepository
import com.joseleandro.flowtask.domain.usecase.CompletedTaskUseCase
import com.joseleandro.flowtask.domain.usecase.DeleteTagByIdUseCase
import com.joseleandro.flowtask.domain.usecase.GetTagFilterUseCase
import com.joseleandro.flowtask.domain.usecase.SaveTagUseCase
import com.joseleandro.flowtask.domain.usecase.SaveTaskUseCase
import com.joseleandro.flowtask.domain.usecase.TagsAllUseCase
import com.joseleandro.flowtask.domain.usecase.TasksGroupByStatusUseCase
import com.joseleandro.flowtask.ui.viewmodel.CreateTagViewModel
import com.joseleandro.flowtask.ui.viewmodel.CreateTaskViewModel
import com.joseleandro.flowtask.ui.viewmodel.FilterTasksUseCase
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import com.joseleandro.flowtask.ui.viewmodel.TagsViewModel
import com.joseleandro.flowtask.ui.viewmodel.TasksViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {

    single<FlowTaskDatabase> {
        Room.databaseBuilder(
            context = androidApplication(),
            FlowTaskDatabase::class.java,
            FLOW_TASK_DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()
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

    single<TaskPreferencesDataStore> {
        TaskPreferencesDataStore(
            context = androidApplication()
        )
    }

    single<TaskDataSource> {
        TaskDataSourceImpl(
            taskDao = get(),
            taskPreferencesDataStore = get()
        )
    }

    single<TagRepository> {
        TagRepositoryImpl(
            tagDataSource = get()
        )
    }

    single<TaskRepository> {
        TaskRepositoryImpl(
            taskDataSource = get()
        )
    }

}

val domainModule = module {

    factory<SaveTagUseCase> {
        SaveTagUseCase(
            tagRepository = get()
        )
    }

    factory<TagsAllUseCase> {
        TagsAllUseCase(
            tagRepository = get()
        )
    }

    factory<SaveTaskUseCase> {

        SaveTaskUseCase(
            taskRepository = get()
        )

    }

    factory<DeleteTagByIdUseCase> {
        DeleteTagByIdUseCase(
            tagRepository = get()
        )
    }

    factory<TasksGroupByStatusUseCase> {
        TasksGroupByStatusUseCase(
            taskRepository = get()
        )
    }

    factory<CompletedTaskUseCase> {
        CompletedTaskUseCase(
            taskRepository = get()
        )
    }

    factory<FilterTasksUseCase> {
        FilterTasksUseCase(
            taskRepository = get()
        )
    }

    factory<GetTagFilterUseCase> {
        GetTagFilterUseCase(
            taskRepository = get()
        )
    }
}

val uiModule = module {

    viewModelOf(::NavigationViewModel)
    viewModelOf(::CreateTaskViewModel)
    viewModelOf(::TagsViewModel)
    viewModelOf(::CreateTagViewModel)
    viewModelOf(::TasksViewModel)
}
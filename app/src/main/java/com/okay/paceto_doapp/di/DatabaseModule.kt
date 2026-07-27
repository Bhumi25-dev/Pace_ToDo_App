package com.okay.paceto_doapp.di

import android.content.Context
import com.okay.paceto_doapp.data.local.FocusDao
import com.okay.paceto_doapp.data.local.TaskDao
import com.okay.paceto_doapp.data.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return TaskDatabase.getInstance(context)
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao
    }

    @Provides
    fun provideFocusDao(database: TaskDatabase): FocusDao {
        return database.focusDao
    }

}

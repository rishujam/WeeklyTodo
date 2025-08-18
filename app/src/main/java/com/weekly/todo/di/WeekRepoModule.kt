package com.weekly.todo.di

import com.weekly.todo.data.repo.WeekRepository
import com.weekly.todo.data.repo.WeekRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeekRepoModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repoImpl: WeekRepositoryImpl): WeekRepository

}
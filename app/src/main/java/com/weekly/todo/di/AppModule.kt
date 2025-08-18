package com.weekly.todo.di

import android.content.Context
import androidx.room.Room
import com.weekly.todo.data.local.WeekDao
import com.weekly.todo.data.local.WeekDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeekDatabase(
        @ApplicationContext context: Context
    ): WeekDatabase {
        return Room.databaseBuilder(
            context,
            WeekDatabase::class.java,
            "week_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeekDao(db: WeekDatabase): WeekDao {
        return db.weekDao()
    }
}
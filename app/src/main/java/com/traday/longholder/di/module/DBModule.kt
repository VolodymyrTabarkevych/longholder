package com.traday.longholder.di.module

import android.content.Context
import com.traday.longholder.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = AppDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideNotificationDao(db: AppDatabase) = db.notificationDao()
}
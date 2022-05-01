package com.traday.longholder.di.module

import com.traday.longholder.data.local.datasource.active.ActiveLocalDataSource
import com.traday.longholder.data.local.datasource.active.IActiveLocalDataSource
import com.traday.longholder.data.local.datasource.notification.INotificationLocalDataSource
import com.traday.longholder.data.local.datasource.notification.NotificationLocalDataSource
import com.traday.longholder.data.local.datasource.user.IUserLocalDataSource
import com.traday.longholder.data.local.datasource.user.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocalDataSourceModule {

    @Binds
    fun bindUserLocalDataSource(source: UserLocalDataSource): IUserLocalDataSource

    @Binds
    fun bindNotificationLocalDataSource(source: NotificationLocalDataSource): INotificationLocalDataSource

    @Binds
    fun bindActiveLocalDataSource(source: ActiveLocalDataSource): IActiveLocalDataSource
}
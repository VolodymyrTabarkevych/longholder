package com.traday.longholder.di.module

import com.traday.longholder.data.remote.datasource.authenticate.AuthenticateRemoteDataSource
import com.traday.longholder.data.remote.datasource.authenticate.IAuthenticateRemoteDataSource
import com.traday.longholder.data.remote.datasource.crypto.CryptoRemoteDataSource
import com.traday.longholder.data.remote.datasource.crypto.ICryptoRemoteDataSource
import com.traday.longholder.data.remote.datasource.notification.INotificationRemoteDataSource
import com.traday.longholder.data.remote.datasource.notification.NotificationRemoteDataSource
import com.traday.longholder.data.remote.datasource.user.IUserRemoteDataSource
import com.traday.longholder.data.remote.datasource.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteDataSourceModule {

    @Binds
    fun bindAuthenticateRemoteDataSource(source: AuthenticateRemoteDataSource): IAuthenticateRemoteDataSource

    @Binds
    fun bindUserRemoteDataSource(source: UserRemoteDataSource): IUserRemoteDataSource

    @Binds
    fun bindNotificationRemoteDataSource(source: NotificationRemoteDataSource): INotificationRemoteDataSource

    @Binds
    fun bindCryptoRemoteDataSource(source: CryptoRemoteDataSource): ICryptoRemoteDataSource
}
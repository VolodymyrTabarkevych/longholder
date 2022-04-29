package com.traday.longholder.di.module

import com.traday.longholder.remote.datasource.authenticate.AuthenticateRemoteDataSource
import com.traday.longholder.remote.datasource.authenticate.IAuthenticateRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteDataSourceModule {

    @Binds
    fun bindAuthenticateRemoteDataSource(source: AuthenticateRemoteDataSource): IAuthenticateRemoteDataSource
}
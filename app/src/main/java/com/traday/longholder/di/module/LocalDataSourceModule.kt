package com.traday.longholder.di.module

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
}
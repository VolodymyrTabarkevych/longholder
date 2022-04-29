package com.traday.longholder.di.module

import com.traday.longholder.remote.rest.IRestBuilder
import com.traday.longholder.remote.rest.RestBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RestModule {

    @Binds
    fun bindNetworkModule(restBuilder: RestBuilder): IRestBuilder
}
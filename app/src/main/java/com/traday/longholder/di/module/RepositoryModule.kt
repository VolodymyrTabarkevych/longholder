package com.traday.longholder.di.module

import com.traday.longholder.data.repository.AuthenticateRepository
import com.traday.longholder.domain.repository.IAuthenticateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindAuthenticateRepository(repository: AuthenticateRepository): IAuthenticateRepository
}
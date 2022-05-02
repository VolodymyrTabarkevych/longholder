package com.traday.longholder.di.module

import com.traday.longholder.data.repository.*
import com.traday.longholder.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindAuthenticateRepository(repository: AuthenticateRepository): IAuthenticateRepository

    @Binds
    fun bindUserRepository(repository: UserRepository): IUserRepository

    @Binds
    fun bindNotificationRepository(repository: NotificationRepository): INotificationRepository

    @Binds
    fun bindActiveRepository(repository: ActiveRepository): IActiveRepository

    @Binds
    fun bindCurrencyRepository(repository: CurrencyRepository): ICurrencyRepository
}
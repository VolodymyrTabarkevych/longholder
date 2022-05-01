package com.traday.longholder.di.module

import com.traday.longholder.data.repository.AuthenticateRepository
import com.traday.longholder.data.repository.CryptoRepository
import com.traday.longholder.data.repository.NotificationRepository
import com.traday.longholder.data.repository.UserRepository
import com.traday.longholder.domain.repository.IAuthenticateRepository
import com.traday.longholder.domain.repository.ICryptoRepository
import com.traday.longholder.domain.repository.INotificationRepository
import com.traday.longholder.domain.repository.IUserRepository
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
    fun bindCryptoRepository(repository: CryptoRepository): ICryptoRepository
}
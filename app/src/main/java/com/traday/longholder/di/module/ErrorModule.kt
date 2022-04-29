package com.traday.longholder.di.module

import com.traday.longholder.domain.error.handlers.ErrorHandler
import com.traday.longholder.domain.error.handlers.IErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ErrorModule {

    @Binds
    fun bindErrorHandler(converter: ErrorHandler): IErrorHandler
}
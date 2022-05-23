package com.traday.longholder.di.module

import com.traday.longholder.domain.servicerunner.IGetNotificationsWorkerRunner
import com.traday.longholder.utils.servicerunner.GetNotificationsWorkerRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface WorkerRunnerModule {

    @Binds
    fun bindNotificationsWorkerRunner(getNotificationsWorkerRunner: GetNotificationsWorkerRunner): IGetNotificationsWorkerRunner
}
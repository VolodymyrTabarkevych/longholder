package com.traday.longholder.di.module

import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.domain.servicerunner.IGetNotificationsWorkerRunner
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.utils.servicerunner.BillingClientRunner
import com.traday.longholder.utils.servicerunner.CreateSubscriptionWorkerRunner
import com.traday.longholder.utils.servicerunner.GetNotificationsWorkerRunner
import com.traday.longholder.utils.servicerunner.StopSubscriptionWorkerRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ServiceRunnerModule {

    @Binds
    fun bindNotificationsWorkerRunner(getNotificationsWorkerRunner: GetNotificationsWorkerRunner): IGetNotificationsWorkerRunner

    @Binds
    fun bindCreateSubscriptionWorkerRunner(createSubscriptionWorkerRunner: CreateSubscriptionWorkerRunner): ICreateSubscriptionWorkerRunner

    @Binds
    fun bindStopSubscriptionWorkerRunner(stopSubscriptionWorkerRunner: StopSubscriptionWorkerRunner): IStopSubscriptionWorkerRunner

    @Binds
    fun bindBillingClientRunner(billingClientRunner: BillingClientRunner): IBillingClientRunner
}
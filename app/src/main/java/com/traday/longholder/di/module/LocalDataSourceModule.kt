package com.traday.longholder.di.module

import com.traday.longholder.data.local.datasource.active.ActiveLocalDataSource
import com.traday.longholder.data.local.datasource.active.IActiveLocalDataSource
import com.traday.longholder.data.local.datasource.currency.CurrencyLocalDataSource
import com.traday.longholder.data.local.datasource.currency.ICurrencyLocalDataSource
import com.traday.longholder.data.local.datasource.notification.INotificationLocalDataSource
import com.traday.longholder.data.local.datasource.notification.NotificationLocalDataSource
import com.traday.longholder.data.local.datasource.settings.ISettingsLocalDataSource
import com.traday.longholder.data.local.datasource.settings.SettingsLocalDataSource
import com.traday.longholder.data.local.datasource.subscription.ISubscriptionLocalDataSource
import com.traday.longholder.data.local.datasource.subscription.SubscriptionLocalDataSource
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

    @Binds
    fun bindNotificationLocalDataSource(source: NotificationLocalDataSource): INotificationLocalDataSource

    @Binds
    fun bindActiveLocalDataSource(source: ActiveLocalDataSource): IActiveLocalDataSource

    @Binds
    fun bindCurrencyLocalDataSource(source: CurrencyLocalDataSource): ICurrencyLocalDataSource

    @Binds
    fun bindSubscriptionLocalDataSource(source: SubscriptionLocalDataSource): ISubscriptionLocalDataSource

    @Binds
    fun bindSettingsLocalDataSource(source: SettingsLocalDataSource): ISettingsLocalDataSource
}
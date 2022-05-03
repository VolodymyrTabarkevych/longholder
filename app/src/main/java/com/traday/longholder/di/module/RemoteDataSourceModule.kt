package com.traday.longholder.di.module

import com.traday.longholder.data.remote.datasource.active.ActiveRemoteDataSource
import com.traday.longholder.data.remote.datasource.active.IActiveRemoteDataSource
import com.traday.longholder.data.remote.datasource.authenticate.AuthenticateRemoteDataSource
import com.traday.longholder.data.remote.datasource.authenticate.IAuthenticateRemoteDataSource
import com.traday.longholder.data.remote.datasource.currency.CurrencyRemoteDataSource
import com.traday.longholder.data.remote.datasource.currency.ICurrencyRemoteDataSource
import com.traday.longholder.data.remote.datasource.notification.INotificationRemoteDataSource
import com.traday.longholder.data.remote.datasource.notification.NotificationRemoteDataSource
import com.traday.longholder.data.remote.datasource.report.IReportRemoteDataSource
import com.traday.longholder.data.remote.datasource.report.ReportRemoteDataSource
import com.traday.longholder.data.remote.datasource.user.IUserRemoteDataSource
import com.traday.longholder.data.remote.datasource.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteDataSourceModule {

    @Binds
    fun bindAuthenticateRemoteDataSource(source: AuthenticateRemoteDataSource): IAuthenticateRemoteDataSource

    @Binds
    fun bindUserRemoteDataSource(source: UserRemoteDataSource): IUserRemoteDataSource

    @Binds
    fun bindNotificationRemoteDataSource(source: NotificationRemoteDataSource): INotificationRemoteDataSource

    @Binds
    fun bindActiveRemoteDataSource(source: ActiveRemoteDataSource): IActiveRemoteDataSource

    @Binds
    fun bindCurrencyRemoteDataSource(source: CurrencyRemoteDataSource): ICurrencyRemoteDataSource

    @Binds
    fun bindReportRemoteDataSource(source: ReportRemoteDataSource): IReportRemoteDataSource
}
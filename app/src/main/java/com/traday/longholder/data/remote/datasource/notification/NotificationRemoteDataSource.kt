package com.traday.longholder.data.remote.datasource.notification

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.NotificationDto
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.safeApiCall
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class NotificationRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<NotificationRemoteDataSource.API>(apiBuilder, preferences),
    INotificationRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getNotifications(): Result<List<NotificationDto>> =
        safeApiCall { api.getNotifications() }

    interface API {

        @GET("User/getMessages")
        suspend fun getNotifications(): Response<List<NotificationDto>>
    }
}
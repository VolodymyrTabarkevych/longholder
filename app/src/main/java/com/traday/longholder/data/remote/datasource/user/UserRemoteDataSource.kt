package com.traday.longholder.data.remote.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.UserDto
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.safeApiCall
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<UserRemoteDataSource.API>(apiBuilder, preferences),
    IUserRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getUser(): Result<UserDto> = safeApiCall {
        api.getUser()
    }

    interface API {

        @GET("User/getuser")
        suspend fun getUser(): Response<UserDto>
    }
}
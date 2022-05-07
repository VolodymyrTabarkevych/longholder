package com.traday.longholder.data.remote.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.data.remote.responsebody.GetActivesResponseBody
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.apiResult
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject

class ActiveRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<ActiveRemoteDataSource.API>(apiBuilder, preferences),
    IActiveRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getActives(): Result<GetActivesResponseBody> =
        apiResult { api.getActives() }

    override suspend fun createActive(active: CreateActiveRequestBody): Result<Unit> =
        apiResult { api.createActive(active) }

    override suspend fun updateActive(active: ActiveDto): Result<Unit> =
        apiResult { api.updateActive(active) }

    override suspend fun deleteActive(id: Int): Result<Unit> =
        apiResult { api.deleteActive(id) }

    interface API {

        @GET("User/getActiveCryptos")
        suspend fun getActives(): Response<GetActivesResponseBody>

        @POST("User/createCrypto")
        suspend fun createActive(@Body active: CreateActiveRequestBody): Response<Unit>

        @PUT("User/updateCryptos")
        suspend fun updateActive(@Body active: ActiveDto): Response<Unit>

        @DELETE("User/deleteCryptos")
        suspend fun deleteActive(@Query("id") id: Int): Response<Unit>
    }
}
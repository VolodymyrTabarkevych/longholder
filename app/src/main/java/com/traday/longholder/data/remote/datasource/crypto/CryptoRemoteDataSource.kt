package com.traday.longholder.data.remote.datasource.crypto

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.data.remote.dto.CryptoDto
import com.traday.longholder.data.remote.rest.IRestBuilder
import com.traday.longholder.extensions.safeApiCall
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject

class CryptoRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<CryptoRemoteDataSource.API>(apiBuilder, preferences),
    ICryptoRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getCryptos(): Result<List<CryptoDto>> =
        safeApiCall { api.getActiveCryptos() }

    override suspend fun createCrypto(crypto: CryptoDto): Result<Unit> =
        safeApiCall { api.createCrypto(crypto) }

    override suspend fun updateCrypto(crypto: CryptoDto): Result<Unit> =
        safeApiCall { api.updateCrypto(crypto) }

    override suspend fun deleteCrypto(id: Int): Result<Unit> =
        safeApiCall { api.deleteCrypto(id) }

    interface API {

        @GET("User/getActiveCryptos")
        suspend fun getActiveCryptos(): Response<List<CryptoDto>>

        @GET("User/getOldCryptos")
        suspend fun getOldCryptos(): Response<List<CryptoDto>>

        @POST("User/createCrypto")
        suspend fun createCrypto(@Body crypto: CryptoDto): Response<Unit>

        @PUT("User/updateCryptos")
        suspend fun updateCrypto(@Body crypto: CryptoDto): Response<Unit>

        @DELETE("User/deleteCryptos")
        suspend fun deleteCrypto(@Query("id") id: Int): Response<Unit>
    }
}
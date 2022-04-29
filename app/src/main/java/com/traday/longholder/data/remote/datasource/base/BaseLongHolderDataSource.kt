package com.traday.longholder.data.remote.datasource.base

import com.traday.longholder.BuildConfig
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.remote.rest.IRestBuilder
import kotlinx.coroutines.runBlocking

abstract class BaseLongHolderDataSource<T>(
    apiBuilder: IRestBuilder,
    private val preferences: IUserPreferences
) : BaseRemoteDataSource<T>(apiBuilder) {

    override val hostUrl: String
        get() = BuildConfig.HOST + "api/"

    override val headersMap: Map<String, String>
        get() = runBlocking {
            mutableMapOf<String, String>().also { headers ->
                preferences.getUserToken()?.let { token ->
                    headers.put(
                        AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $token"
                    )
                }
            }
        }

    companion object {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"
    }
}


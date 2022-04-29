package com.traday.longholder.remote.datasource.authenticate

import com.traday.longholder.data.base.Result
import com.traday.longholder.extensions.safeApiCall
import com.traday.longholder.local.preferences.user.IUserPreferences
import com.traday.longholder.remote.datasource.base.BaseLongHolderDataSource
import com.traday.longholder.remote.requestbody.LoginRequestBody
import com.traday.longholder.remote.requestbody.RegisterRequestBody
import com.traday.longholder.remote.responsebody.LoginResponseBody
import com.traday.longholder.remote.rest.IRestBuilder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class AuthenticateRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder,
    preferences: IUserPreferences
) : BaseLongHolderDataSource<AuthenticateRemoteDataSource.API>(apiBuilder, preferences),
    IAuthenticateRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun register(userName: String, email: String, password: String): Result<Unit> =
        safeApiCall {
            api.register(
                RegisterRequestBody(
                    userName = userName,
                    email = email,
                    password = password
                )
            )
        }

    override suspend fun login(userName: String, password: String): Result<LoginResponseBody> =
        safeApiCall {
            api.login(LoginRequestBody(userName = userName, password = password))
        }

    interface API {

        @POST("Authenticate/register")
        suspend fun register(@Body registerRequestBody: RegisterRequestBody): Response<Unit>

        @POST("Authenticate/login")
        suspend fun login(@Body loginRequestBody: LoginRequestBody): Response<LoginResponseBody>
    }
}
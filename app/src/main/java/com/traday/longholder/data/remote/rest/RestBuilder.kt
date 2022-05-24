package com.traday.longholder.data.remote.rest

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.data.remote.interceptors.NetworkConnectionInterceptor
import com.traday.longholder.data.remote.responsebody.base.ErrorResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RestBuilder @Inject constructor(@ApplicationContext val context: Context) : IRestBuilder {

    private val gson by lazy {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    override fun build(
        baseUrl: String,
        requestBuilder: (builder: Request.Builder) -> Unit
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(getOkHttpClient(requestBuilder))
            .build()

    private fun getOkHttpClient(requestBuilder: (builder: Request.Builder) -> Unit): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
                requestBuilder(request)
                chain.proceed(request.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()

    private val errorHandlingInterceptor by lazy {
        Interceptor { chain ->
            val response = chain.proceed(chain.request().newBuilder().build())
            if (!response.isSuccessful) {
                val errorDto = ErrorResponse(
                    response.code,
                    parseServerErrorResponse(response)
                )
                throw BaseException.NetworkRequestException(errorDto)
            } else if (response.body == null) {
                throw BaseException.NoResponseContentException()
            }
            response
        }
    }

    private fun parseServerErrorResponse(response: Response): String {
        val responseString = response.body!!.string()
        return try {
            val jsonObject = JSONObject(responseString)
            val message = jsonObject.getString(ERROR_RESPONSE_NAME_MESSAGE)
            message
        } catch (e: Exception) {
            responseString.ifEmpty {
                response.message
            }
        }
    }

    companion object {

        private const val CONNECTION_TIMEOUT = 10L
        private const val WRITE_TIMEOUT = 20L
        private const val READ_TIMEOUT = 30L

        const val CONTENT_TYPE_HEADER = "Content-Type"
        const val CONTENT_TYPE_HEADER_VALUE = "application/json"
        private const val ERROR_RESPONSE_NAME_STATUS = "status"
        private const val ERROR_RESPONSE_NAME_MESSAGE = "message"
    }
}
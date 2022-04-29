package com.traday.longholder.local.preferences.user

interface IUserPreferences {

    suspend fun setUserToken(userToken: String?): Unit

    suspend fun getUserToken(): String?
}
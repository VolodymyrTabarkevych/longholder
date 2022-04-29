package com.traday.longholder.remote.responsebody.authenticate

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("token") val token: String
)
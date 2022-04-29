package com.traday.longholder.remote.responsebody

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("token") val token: String
)
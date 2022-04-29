package com.traday.longholder.data.remote.responsebody

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("token") val token: String
)
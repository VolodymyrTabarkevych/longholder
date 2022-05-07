package com.traday.longholder.data.remote.requestbody

import com.google.gson.annotations.SerializedName

data class RegisterRequestBody(
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("confirmPassword") val confirmPassword: String
)
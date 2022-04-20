package com.traday.longholder.domain.model

import androidx.annotation.DrawableRes

data class Notification(
    @DrawableRes val icon: Int,
    val cryptoName: String,
    val status: String,
    val earned: String,
    val date: String
)
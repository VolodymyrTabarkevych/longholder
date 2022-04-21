package com.traday.longholder.domain.model

import androidx.annotation.DrawableRes

data class Coin(
    @DrawableRes val icon: Int,
    val name: String
)
package com.traday.longholder.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Active(
    @DrawableRes val icon: Int,
    val name: String,
    val amount: String,
    val amountInDollars: String,
    val date: String,
    val summaryDayOfPurchase: String,
    val summaryToday: String,
    val profit: String,
    val cryptoPrice: String
) : Parcelable
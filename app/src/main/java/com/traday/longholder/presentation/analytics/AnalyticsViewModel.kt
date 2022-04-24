package com.traday.longholder.presentation.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.R
import com.traday.longholder.domain.model.Coin
import com.traday.longholder.presentation.base.BaseViewModel

class AnalyticsViewModel : BaseViewModel() {

    private val _coinsLiveData = MutableLiveData<List<Coin>>()
    val coinsLiveData: LiveData<List<Coin>> get() = _coinsLiveData

    private val _showAnalyticsScreen = MutableLiveData(false)
    val showAnalyticsScreen: LiveData<Boolean> get() = _showAnalyticsScreen

    init {
        getCoins()
    }

    private fun getCoins() {
        val coins = getTestDataForCoins()
        _coinsLiveData.postValue(coins)
    }

    fun makeSubscription() {
        _showAnalyticsScreen.postValue(true)
    }

    private fun getTestDataForCoins(): List<Coin> {
        return listOf(
            Coin(
                icon = R.drawable.img_btc,
                name = "Bitcoin"
            ),
            Coin(
                icon = R.drawable.img_eth,
                name = "Ethereum"
            )
        )
    }
}
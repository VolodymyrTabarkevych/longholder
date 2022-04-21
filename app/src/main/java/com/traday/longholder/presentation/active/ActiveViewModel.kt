package com.traday.longholder.presentation.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.R
import com.traday.longholder.domain.model.Coin
import com.traday.longholder.presentation.base.BaseViewModel

class ActiveViewModel : BaseViewModel() {

    private val _coinsLiveData = MutableLiveData<List<Coin>>()
    val coinsLiveData: LiveData<List<Coin>> get() = _coinsLiveData

    init {
        getCoins()
    }

    private fun getCoins() {
        val coins = getTestDataForCoins()
        _coinsLiveData.postValue(coins)
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
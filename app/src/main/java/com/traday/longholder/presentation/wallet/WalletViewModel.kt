package com.traday.longholder.presentation.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.usecase.SubscribeOnActivesUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    subscribeOnActivesUseCase: SubscribeOnActivesUseCase
) : BaseViewModel() {

    val subscribeOnActivesLiveData: LiveData<Resource<List<Active>>> = executeUseCase(
        useCase = subscribeOnActivesUseCase,
        params = SubscribeOnActivesUseCase.Params(true),
        showDialogOnError = false
    ).asLiveData(timeoutInMs = 10000)
}
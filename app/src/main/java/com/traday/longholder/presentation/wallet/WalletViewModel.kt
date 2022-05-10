package com.traday.longholder.presentation.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.usecase.CreateActiveUseCase
import com.traday.longholder.domain.usecase.GetActivesUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getActivesUseCase: GetActivesUseCase,
    private val createActiveUseCase: CreateActiveUseCase
) : BaseViewModel() {

    private val _getCryptosLiveData =
        executeUseCase(
            useCase = getActivesUseCase,
            showDialogOnError = false,
            params = EmptyParams()
        ).asLiveData()
    val getCryptosLiveData: LiveData<Resource<List<Active>>> get() = _getCryptosLiveData

}
package com.traday.longholder.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.entities.BaseError
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

open class BaseViewModel : ViewModel() {

    private val noInternetConnectionUseCases = mutableListOf<suspend () -> Unit>()

    private val _noInternetConnectionLiveData = MutableLiveData(false)
    val noInternetConnectionLiveData: LiveData<Boolean>
        get() = _noInternetConnectionLiveData

    fun <P : EmptyParams, R : Any> executeUseCase(
        useCase: BaseUseCase<P, R>,
        params: P,
        onResult: suspend (Resource<R>) -> Unit = {}
    ) {
        viewModelScope.launch {
            useCase.execute(params) {
                when (it) {
                    is Resource.Error -> {
                        when (it.error) {
                            is BaseError.NetworkConnectionError -> {
                                noInternetConnectionUseCases.add {
                                    executeUseCase(
                                        useCase,
                                        params,
                                        onResult
                                    )
                                }
                                _noInternetConnectionLiveData.postValue(true)
                                onResult(it)
                            }
                            else -> onResult(it)
                        }
                    }
                    is Resource.Success -> {
                        onResult(it)
                    }
                    else -> onResult(it)
                }
            }
        }
    }

    fun executeNotLoadedUseCases() {
        val bufferedUseCases = noInternetConnectionUseCases.toImmutableList()
        viewModelScope.launch {
            bufferedUseCases.forEach { useCase ->
                noInternetConnectionUseCases.removeFirstOrNull()
                useCase()
            }
        }
    }
}
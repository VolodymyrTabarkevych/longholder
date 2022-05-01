package com.traday.longholder.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.entities.BaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

open class BaseViewModel : ViewModel() {

    private val noInternetConnectionUseCases = mutableListOf<suspend () -> Unit>()

    private val _noInternetConnectionLiveData = EventLiveData<Unit>()
    val noInternetConnectionLiveData: LiveData<Unit> get() = _noInternetConnectionLiveData

    protected val _unauthorizedLiveData = EventLiveData<Resource<Unit>>()
    val unauthorizedLiveData: LiveData<Resource<Unit>> get() = _unauthorizedLiveData

    fun <P : EmptyParams, R : Any> executeUseCase(
        useCase: BaseUseCase<P, R>,
        params: P,
        onResult: suspend (Resource<R>) -> Unit = {}
    ) {
        viewModelScope.launch {
            useCase.execute(params) {
                if (it is Resource.Error) {
                    when (it.error) {
                        is BaseError.NetworkConnectionError -> {
                            noInternetConnectionUseCases.add {
                                executeUseCase(
                                    useCase,
                                    params,
                                    onResult
                                )
                            }
                            _noInternetConnectionLiveData.postValue(Unit)
                            onResult(Resource.Loading)
                        }
                        is BaseError.UnauthorizedError -> {
                            _unauthorizedLiveData.postValue(it)
                            onResult(Resource.Loading)
                        }
                        else -> onResult(it)
                    }
                } else {
                    onResult(it)
                }
            }
        }
    }

    fun <P : EmptyParams, R : Any> executeUseCase(
        useCase: FlowUseCase<P, R>,
        params: P
    ): Flow<Resource<R>> {
        return useCase.execute(params).map {
            return@map if (it is Resource.Error) {
                when (it.error) {
                    is BaseError.NetworkConnectionError -> {
                        _noInternetConnectionLiveData.postValue(Unit)
                        Resource.Loading
                    }
                    is BaseError.UnauthorizedError -> {
                        _unauthorizedLiveData.postValue(it)
                        Resource.Loading
                    }
                    else -> it
                }
            } else {
                it
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
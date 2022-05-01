package com.traday.longholder.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

abstract class FlowUseCase<in P : EmptyParams, out R : Any> {

    abstract fun run(params: P): Flow<Resource<R>>

    fun execute(params: P): Flow<Resource<R>> {
        return run(params).onStart { emit(Resource.Loading) }
    }
}
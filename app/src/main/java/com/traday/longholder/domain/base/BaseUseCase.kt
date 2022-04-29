package com.traday.longholder.domain.base

abstract class BaseUseCase<in P : EmptyParams, out R : Any> {

    abstract suspend fun run(params: P): Resource<R>

    suspend fun execute(
        params: P,
        onResult: suspend (Resource<R>) -> Unit = {}
    ) {
        onResult(Resource.Loading)
        onResult(run(params))
    }
}
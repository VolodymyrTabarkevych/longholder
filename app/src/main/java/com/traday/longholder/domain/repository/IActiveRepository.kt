package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.domain.model.Active
import kotlinx.coroutines.flow.Flow

interface IActiveRepository {

    fun getActives(): Flow<Result<List<ActiveEntity>>>

    suspend fun createActive(active: CreateActiveRequestBody): Result<Unit>

    suspend fun updateActive(active: Active): Result<Unit>

    suspend fun deleteActive(id: Int): Result<Unit>
}
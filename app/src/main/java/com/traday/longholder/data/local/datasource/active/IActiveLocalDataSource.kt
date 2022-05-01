package com.traday.longholder.data.local.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.ActiveEntity
import kotlinx.coroutines.flow.Flow

interface IActiveLocalDataSource {

    suspend fun saveOrUpdateActive(vararg: ActiveEntity): Result<Unit>

    suspend fun saveOrUpdateActive(actives: List<ActiveEntity>): Result<Unit>

    fun getActives(): Flow<Result<List<ActiveEntity>>>

    suspend fun deleteActive(id: Int): Result<Unit>

    suspend fun deleteActive(): Result<Unit>
}
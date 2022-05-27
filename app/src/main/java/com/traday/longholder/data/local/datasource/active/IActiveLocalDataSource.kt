package com.traday.longholder.data.local.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.ActiveEntity
import kotlinx.coroutines.flow.Flow

interface IActiveLocalDataSource {

    suspend fun insertOrUpdateActive(active: ActiveEntity): Result<Unit>

    suspend fun insertOrUpdateActives(actives: List<ActiveEntity>): Result<List<ActiveEntity>>

    suspend fun getActives(): Result<List<ActiveEntity>>

    fun subscribeOnActives(): Flow<Result<List<ActiveEntity>>>

    suspend fun deleteActive(id: Int): Result<Unit>

    suspend fun deleteAllActives(): Result<Unit>
}
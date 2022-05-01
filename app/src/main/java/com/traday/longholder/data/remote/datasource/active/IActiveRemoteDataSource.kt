package com.traday.longholder.data.remote.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.ActiveDto

interface IActiveRemoteDataSource {

    suspend fun getActives(): Result<List<ActiveDto>>

    suspend fun createActive(active: ActiveDto): Result<Unit>

    suspend fun updateActive(active: ActiveDto): Result<Unit>

    suspend fun deleteActive(id: Int): Result<Unit>
}
package com.traday.longholder.data.remote.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.data.remote.responsebody.GetActivesResponseBody

interface IActiveRemoteDataSource {

    suspend fun getActives(): Result<GetActivesResponseBody>

    suspend fun createActive(active: CreateActiveRequestBody): Result<Unit>

    suspend fun updateActive(active: ActiveDto): Result<Unit>

    suspend fun deleteActive(id: Int): Result<Unit>
}
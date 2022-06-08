package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.domain.model.Active
import kotlinx.coroutines.flow.Flow

interface IActiveRepository {

    suspend fun getActives(sync: Boolean): Result<List<ActiveEntity>>

    suspend fun getEndedActiveById(id: Int): Result<ActiveDto>

    fun subscribeOnActives(syncAtStart: Boolean): Flow<Result<List<ActiveEntity>>>

    suspend fun createActive(
        name: String?,
        valueOfCrypto: String,
        currentCurrencyPrice: Double,
        cryptoPriceOnStart: Double,
        dateOfEnd: String,
        comment: String?,
        linkToImage: String?,
        symbol: String
    ): Result<Unit>

    suspend fun updateActive(active: Active): Result<Unit>

    suspend fun deleteActive(id: Int): Result<Unit>
}
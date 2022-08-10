package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.data.local.datasource.active.IActiveLocalDataSource
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.active.IActiveRemoteDataSource
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.data.remote.requestbody.UpdateActiveRequestBody
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.repository.IActiveRepository
import com.traday.longholder.extensions.formatDateClientFormatToServerFormatOrEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ActiveRepository @Inject constructor(
    private val activeRemoteDataSource: IActiveRemoteDataSource,
    private val activeLocalDataSource: IActiveLocalDataSource
) : IActiveRepository {

    override suspend fun getActives(sync: Boolean): Result<List<ActiveEntity>> {
        if (!sync) return activeLocalDataSource.getActives()
        val remoteActivesResult = activeRemoteDataSource.getActives()
        val localActivesResult = activeLocalDataSource.getActives()

        return when (remoteActivesResult) {
            is Result.Error -> {
                if (localActivesResult is Result.Success && localActivesResult.data.isNotEmpty()) {
                    localActivesResult
                } else {
                    remoteActivesResult
                }
            }
            is Result.Success -> {
                val mappedCurrencies =
                    remoteActivesResult.data.actives.map { it.toEntity() }
                activeLocalDataSource.insertOrUpdateActives(mappedCurrencies)
            }
        }
    }

    override suspend fun getEndedActiveById(id: Int): Result<ActiveDto> {
        return activeRemoteDataSource.getEndedActiveById(id)
    }

    override fun subscribeOnActives(syncAtStart: Boolean): Flow<Result<List<ActiveEntity>>> {
        return activeLocalDataSource.subscribeOnActives()
            .onStart {
                if (!syncAtStart) return@onStart
                val remoteResult = activeRemoteDataSource.getActives()
                if (remoteResult is Result.Success) {
                    val mappedItems = remoteResult.data.actives.map { it.toEntity() }
                    activeLocalDataSource.insertOrUpdateActives(mappedItems)
                } else if (remoteResult is Result.Error) {
                    if (remoteResult.error is BaseException.NoAvailableActives) {
                        activeLocalDataSource.deleteAllActives()
                    } else {
                        emit(remoteResult)
                    }
                }
            }
    }

    override suspend fun createActive(
        name: String?,
        priceOnStart: String,
        valueOfCrypto: String,
        wantedPercents: String,
        currentCurrencyPrice: Double,
        dateOfEnd: String,
        comment: String?,
        linkToImage: String?,
        symbol: String,
        indexOnExchange: String
    ): Result<Unit> {
        val createRemoteActiveResult = activeRemoteDataSource.createActive(
            CreateActiveRequestBody(
                name = name,
                valueOfCrypto = valueOfCrypto.toDouble(),
                wantedPercents = wantedPercents.toDouble(),
                currentCurrencyPrice = currentCurrencyPrice,
                cryptoPriceOnStart = priceOnStart.toDouble(),
                dateOfEnd = dateOfEnd.formatDateClientFormatToServerFormatOrEmpty(),
                comment = comment,
                linkToImage = linkToImage,
                symbol = symbol,
                indexOnExchange = indexOnExchange
            )
        )
        return if (createRemoteActiveResult is Result.Success) {
            val remoteActivesResult = activeRemoteDataSource.getActives()
            if (remoteActivesResult is Result.Success) {
                val mappedItems = remoteActivesResult.data.actives.map { it.toEntity() }
                activeLocalDataSource.insertOrUpdateActives(mappedItems)
                Result.Success(Unit)
            } else {
                createRemoteActiveResult
            }
        } else {
            createRemoteActiveResult
        }
    }

    override suspend fun updateActive(active: Active): Result<Unit> {
        val createRemoteActiveResult = activeRemoteDataSource.updateActive(
            UpdateActiveRequestBody(
                id = active.id,
                name = active.name,
                valueOfCrypto = active.valueOfCrypto,
                currentCurrencyPrice = active.currentCurrencyPrice,
                cryptoPriceOnStart = active.cryptoPriceOnStart,
                dateOfEnd = active.dateOfEnd.formatDateClientFormatToServerFormatOrEmpty(),
                comment = active.comment,
                linkToImage = active.linkToImage,
                symbol = active.symbol
            )
        )
        return if (createRemoteActiveResult is Result.Success) {
            activeLocalDataSource.insertOrUpdateActive(active.toEntity())
        } else {
            createRemoteActiveResult
        }
    }

    override suspend fun deleteActive(id: Int): Result<Unit> {
        val createRemoteActiveResult = activeRemoteDataSource.deleteActive(id)
        return if (createRemoteActiveResult is Result.Success) {
            activeLocalDataSource.deleteActive(id)
        } else {
            createRemoteActiveResult
        }
    }
}
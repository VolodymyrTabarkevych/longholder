package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.active.IActiveLocalDataSource
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.active.IActiveRemoteDataSource
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.data.remote.requestbody.CreateActiveRequestBody
import com.traday.longholder.domain.repository.IActiveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ActiveRepository @Inject constructor(
    private val activeRemoteDataSource: IActiveRemoteDataSource,
    private val activeLocalDataSource: IActiveLocalDataSource
) : IActiveRepository {

    override fun getActives(): Flow<Result<List<ActiveEntity>>> {
        return activeLocalDataSource.getActives()
            .onStart {
                val remoteResult = activeRemoteDataSource.getActives()
                if (remoteResult is Result.Success) {
                    val mappedItems = remoteResult.data.actives.map { it.toEntity() }
                    activeLocalDataSource.saveOrUpdateActive(mappedItems)
                } else if (remoteResult is Result.Error) {
                    emit(remoteResult)
                }
            }
    }

    override suspend fun createActive(active: CreateActiveRequestBody): Result<Unit> {
        val createRemoteActiveResult = activeRemoteDataSource.createActive(active)
        return if (createRemoteActiveResult is Result.Success) {
            val remoteActivesResult = activeRemoteDataSource.getActives()
            if (remoteActivesResult is Result.Success) {
                val mappedItems = remoteActivesResult.data.actives.map { it.toEntity() }
                activeLocalDataSource.saveOrUpdateActive(mappedItems)
            } else {
                createRemoteActiveResult
            }
        } else {
            createRemoteActiveResult
        }
    }

    override suspend fun updateActive(active: ActiveDto): Result<Unit> {
        val createRemoteActiveResult = activeRemoteDataSource.updateActive(active)
        return if (createRemoteActiveResult is Result.Success) {
            activeLocalDataSource.saveOrUpdateActive(active.toEntity())
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
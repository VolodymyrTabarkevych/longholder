package com.traday.longholder.data.local.datasource.active

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.ActiveDao
import com.traday.longholder.data.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActiveLocalDataSource @Inject constructor(
    private val activeDao: ActiveDao
) : BaseLocalDataSource(), IActiveLocalDataSource {

    override suspend fun saveOrUpdateActive(vararg: ActiveEntity): Result<Unit> =
        result { activeDao.insertData(vararg) }

    override suspend fun saveOrUpdateActive(actives: List<ActiveEntity>): Result<Unit> =
        result { activeDao.insertData(actives) }

    override fun getActives(): Flow<Result<List<ActiveEntity>>> =
        flowResult { activeDao.getActives() }

    override suspend fun deleteActive(id: Int): Result<Unit> =
        result { activeDao.deleteActiveById(id) }

    override suspend fun deleteActive(): Result<Unit> =
        result { activeDao.deleteActive() }
}
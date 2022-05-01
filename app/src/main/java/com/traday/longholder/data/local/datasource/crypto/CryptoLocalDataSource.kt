package com.traday.longholder.data.local.datasource.crypto

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.CryptoDao
import com.traday.longholder.data.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.extensions.safeFlow
import com.traday.longholder.extensions.safeOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoLocalDataSource @Inject constructor(
    private val cryptoDao: CryptoDao
) : BaseLocalDataSource(), ICryptoLocalDataSource {

    override suspend fun saveOrUpdateCryptos(vararg: CryptoEntity): Result<Unit> =
        safeOperation { cryptoDao.insertData(vararg) }

    override suspend fun saveOrUpdateCryptos(cryptos: List<CryptoEntity>): Result<Unit> =
        safeOperation { cryptoDao.insertData(cryptos) }

    override fun getCryptos(): Flow<Result<List<CryptoEntity>>> =
        safeFlow { cryptoDao.getCryptos() }

    override suspend fun deleteCrypto(id: Int): Result<Unit> =
        safeOperation { cryptoDao.deleteCryptoById(id) }

    override suspend fun deleteCryptos(): Result<Unit> =
        safeOperation { cryptoDao.deleteCryptos() }
}
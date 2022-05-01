package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.crypto.ICryptoLocalDataSource
import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.crypto.ICryptoRemoteDataSource
import com.traday.longholder.data.remote.dto.CryptoDto
import com.traday.longholder.domain.repository.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoRemoteDataSource: ICryptoRemoteDataSource,
    private val cryptoLocalDataSource: ICryptoLocalDataSource
) : ICryptoRepository {

    override fun getCryptos(): Flow<Result<List<CryptoEntity>>> {
        return cryptoLocalDataSource.getCryptos()
            .onStart {
                val remoteCryptoResult = cryptoRemoteDataSource.getCryptos()
                if (remoteCryptoResult is Result.Success) {
                    val mappedCryptos = remoteCryptoResult.data.map { it.toEntity() }
                    cryptoLocalDataSource.saveOrUpdateCryptos(mappedCryptos)
                } else if (remoteCryptoResult is Result.Error) {
                    emit(remoteCryptoResult)
                }
            }
    }

    override suspend fun createCrypto(crypto: CryptoDto): Result<Unit> {
        val createRemoteCryptoResult = cryptoRemoteDataSource.createCrypto(crypto)
        return if (createRemoteCryptoResult is Result.Success) {
            cryptoLocalDataSource.saveOrUpdateCryptos(crypto.toEntity())
        } else {
            createRemoteCryptoResult
        }
    }

    override suspend fun updateCrypto(crypto: CryptoDto): Result<Unit> {
        val createRemoteCryptoResult = cryptoRemoteDataSource.updateCrypto(crypto)
        return if (createRemoteCryptoResult is Result.Success) {
            cryptoLocalDataSource.saveOrUpdateCryptos(crypto.toEntity())
        } else {
            createRemoteCryptoResult
        }
    }

    override suspend fun deleteCrypto(id: Int): Result<Unit> {
        val createRemoteCryptoResult = cryptoRemoteDataSource.deleteCrypto(id)
        return if (createRemoteCryptoResult is Result.Success) {
            cryptoLocalDataSource.deleteCrypto(id)
        } else {
            createRemoteCryptoResult
        }
    }
}
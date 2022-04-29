package com.traday.longholder.domain.repository

import com.traday.longholder.remote.dto.CryptoDto

interface ICryptosRepository {

    suspend fun getCryptos(): Result<List<CryptoDto>>

    suspend fun addCrypto(crypto: CryptoDto): Result<CryptoDto>

    suspend fun getCryptoById(id: Int): Result<CryptoDto>

    suspend fun updateCrypto(crypto: CryptoDto): Result<Unit>

    suspend fun deleteCryptoById(id: Int): Result<Unit>
}
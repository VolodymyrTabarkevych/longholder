package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.datasource.calc.ICalcRemoteDataSource
import com.traday.longholder.domain.repository.ICalcRepository
import javax.inject.Inject

class CalcRepository @Inject constructor(
    private val calcRemoteDataSource: ICalcRemoteDataSource
) : ICalcRepository {

    override suspend fun percentFrom(percent: Double, number: Double): Result<String> {
        return calcRemoteDataSource.percentFrom(percent, number)
    }

    override suspend fun numberFromNumber(numberThat: Double, numberFrom: Double): Result<String> {
        return calcRemoteDataSource.numberFromNumber(numberThat, numberFrom)
    }

    override suspend fun addPercent(percent: Double, number: Double): Result<String> {
        return calcRemoteDataSource.addPercent(percent, number)
    }

    override suspend fun subtractPercent(percent: Double, number: Double): Result<String> {
        return calcRemoteDataSource.subtractPercent(percent, number)
    }
}
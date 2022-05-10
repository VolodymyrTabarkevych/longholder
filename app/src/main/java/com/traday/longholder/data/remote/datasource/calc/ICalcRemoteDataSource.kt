package com.traday.longholder.data.remote.datasource.calc

import com.traday.longholder.data.base.Result

interface ICalcRemoteDataSource {

    suspend fun percentFrom(percent: Double, number: Double): Result<String>

    suspend fun numberFromNumber(numberThat: Double, numberFrom: Double): Result<String>

    suspend fun addPercent(percent: Double, number: Double): Result<String>

    suspend fun subtractPercent(percent: Double, number: Double): Result<String>
}
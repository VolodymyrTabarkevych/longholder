package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result

interface ICalcRepository {

    suspend fun percentFrom(percent: Double, number: Double): Result<String>

    suspend fun numberFromNumber(numberThat: Double, numberFrom: Double): Result<String>

    suspend fun addPercent(percent: Double, number: Double): Result<String>

    suspend fun subtractPercent(percent: Double, number: Double): Result<String>
}
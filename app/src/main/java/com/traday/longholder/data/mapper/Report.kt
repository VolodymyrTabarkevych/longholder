package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.ReportEntity
import com.traday.longholder.data.remote.dto.ReportDto
import com.traday.longholder.domain.model.Report
import com.traday.longholder.utils.EMPTY_STRING

fun ReportDto.toEntity() = ReportEntity(
    id = id,
    name = name ?: EMPTY_STRING,
    currencyCode = currencyCode ?: EMPTY_STRING,
    profit = profit,
    allMoney = allMoney,
    countOfCrypto = countOfCrypto,
    percents = percents,
    priceNow = priceNow,
    coinName = coinName ?: EMPTY_STRING,
    cryptoMoney = cryptoMoney.toEntity(),
    dateOfReport = dateOfReport
)

fun ReportEntity.toDomain() = Report(
    id = id,
    name = name,
    currencyCode = currencyCode,
    profit = profit,
    allMoney = allMoney,
    countOfCrypto = countOfCrypto,
    percents = percents,
    priceNow = priceNow,
    coinName = coinName,
    cryptoMoney = cryptoMoney.toDomain(),
    dateOfReport = dateOfReport
)
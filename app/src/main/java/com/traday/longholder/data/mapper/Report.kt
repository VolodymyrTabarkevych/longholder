package com.traday.longholder.data.mapper

import com.traday.longholder.data.remote.dto.ReportDto
import com.traday.longholder.domain.model.Report

fun ReportDto.toDomain() = Report(
    id = id,
    name = name,
    currencyCode = currencyCode,
    profit = profit,
    allMoney = allMoney,
    countOfCrypto = countOfCrypto,
    percents = percents,
    priceNow = priceNow,
    coinName = coinName,
    actives = actives?.map { it.toDomain() } ?: emptyList(),
    dateOfReport = dateOfReport
)
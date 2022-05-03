package com.traday.longholder.data.mapper

import com.traday.longholder.data.remote.dto.ReportDto
import com.traday.longholder.domain.model.Report
import com.traday.longholder.extensions.replaceDotWithComma

fun ReportDto.toDomain() = Report(
    id = id,
    name = name,
    currencyCode = currencyCode,
    profit = profit,
    profitFormatted = profit.replaceDotWithComma(),
    allMoney = allMoney,
    allMoneyFormatted = allMoney.replaceDotWithComma(),
    countOfCrypto = countOfCrypto,
    countOfCryptoFormatted = countOfCrypto.toString(),
    percents = percents,
    priceNow = priceNow,
    priceNowFormatted = priceNow.replaceDotWithComma(),
    coinName = coinName,
    actives = actives?.map { it.toDomain() } ?: emptyList(),
    dateOfReport = dateOfReport
)
package com.traday.longholder.domain.model

data class User(
    val userName: String,
    val email: String,
    val currencyCode: String/*,
    val cryptos: List<Crypto>,
    val messages: List<Message>,
    val reports: List<Report>*/
)
package com.traday.longholder.domain.model

data class Notification(
    val id: Int,
    val name: String,
    val valueOfMessage: String,
    val linkToTheImage: String,
    val dateOfSent: String,
    val dateOfStart: String
)
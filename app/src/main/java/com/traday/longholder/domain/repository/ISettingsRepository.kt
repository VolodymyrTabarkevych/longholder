package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.enums.Language
import kotlinx.coroutines.flow.Flow


interface ISettingsRepository {

    suspend fun setSelectedLanguage(language: Language): Result<Language>

    suspend fun getSelectedLanguage(): Result<Language>

    fun subscribeOnSelectedLanguage(): Flow<Result<Language>>
}
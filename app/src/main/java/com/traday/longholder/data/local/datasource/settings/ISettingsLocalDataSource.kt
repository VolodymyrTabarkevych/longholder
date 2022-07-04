package com.traday.longholder.data.local.datasource.settings

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.enums.Language
import kotlinx.coroutines.flow.Flow

interface ISettingsLocalDataSource {

    suspend fun setSelectedLanguage(language: Language): Result<Language>

    suspend fun getSelectedLanguage(): Result<Language>

    fun subscribeOnSelectedLanguage(): Flow<Result<Language>>
}
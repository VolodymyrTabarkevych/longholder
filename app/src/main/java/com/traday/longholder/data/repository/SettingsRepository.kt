package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.settings.ISettingsLocalDataSource
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val settingsLocalDataSource: ISettingsLocalDataSource
) : ISettingsRepository {

    override suspend fun setSelectedLanguage(language: Language): Result<Language> {
        return settingsLocalDataSource.setSelectedLanguage(language)
    }

    override suspend fun getSelectedLanguage(): Result<Language> {
        return settingsLocalDataSource.getSelectedLanguage()
    }

    override fun subscribeOnSelectedLanguage(): Flow<Result<Language>> {
        return settingsLocalDataSource.subscribeOnSelectedLanguage()
    }
}
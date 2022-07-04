package com.traday.longholder.data.local.datasource.settings

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.settings.ISettingsPreferences
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import com.traday.longholder.domain.enums.Language
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val settingsPreferences: ISettingsPreferences
) : ISettingsLocalDataSource {

    override suspend fun setSelectedLanguage(language: Language): Result<Language> = result {
        settingsPreferences.setSelectedLanguage(language)
        settingsPreferences.getSelectedLanguage()
    }

    override suspend fun getSelectedLanguage(): Result<Language> = result {
        settingsPreferences.getSelectedLanguage()
    }

    override fun subscribeOnSelectedLanguage(): Flow<Result<Language>> = flowResult {
        settingsPreferences.subscribeOnSelectedLanguage()
    }
}
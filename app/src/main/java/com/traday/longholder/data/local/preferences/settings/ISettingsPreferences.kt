package com.traday.longholder.data.local.preferences.settings

import com.traday.longholder.domain.enums.Language
import kotlinx.coroutines.flow.Flow

interface ISettingsPreferences {

    suspend fun setSelectedLanguage(language: Language)

    suspend fun getSelectedLanguage(): Language

    fun subscribeOnSelectedLanguage(): Flow<Language>
}
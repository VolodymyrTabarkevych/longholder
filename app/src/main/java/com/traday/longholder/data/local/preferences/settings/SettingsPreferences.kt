package com.traday.longholder.data.local.preferences.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.traday.longholder.data.local.preferences.base.BasePreferences
import com.traday.longholder.di.qualifire.SettingsPreferences
import com.traday.longholder.domain.enums.Language
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class SettingsPreferences @Inject constructor(
    @SettingsPreferences dataStore: DataStore<Preferences>
) : BasePreferences(dataStore), ISettingsPreferences {

    override suspend fun setSelectedLanguage(language: Language) = write {
        it[SELECTED_LANGUAGE] = language.name
    }

    override suspend fun getSelectedLanguage(): Language = read {
        Language.valueOf(it[SELECTED_LANGUAGE] ?: getDefaultLanguage().name)
    }

    override fun subscribeOnSelectedLanguage(): Flow<Language> = subscribe {
        Language.valueOf(it[SELECTED_LANGUAGE] ?: getDefaultLanguage().name)
    }

    private fun getDefaultLanguage(): Language {
        val defaultLocaleName = Locale.getDefault().language
        return if (defaultLocaleName == Language.ENGLISH.shortName) {
            Language.ENGLISH
        } else {
            Language.UKRAINIAN
        }
    }

    companion object {

        private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    }
}
package com.traday.longholder.presentation.base

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.traday.longholder.extensions.app
import kotlinx.coroutines.runBlocking
import java.util.*

abstract class BaseActivity(@LayoutRes layoutResId: Int) : AppCompatActivity(layoutResId) {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(updateBaseContextLocale(context))
    }

    private fun updateBaseContextLocale(context: Context): Context? = runBlocking {
        val language: String = context.app.settingsPreferences.getSelectedLanguage().shortName
        val locale = Locale(language)
        Locale.setDefault(locale)
        updateResourcesLocale(context, locale)
    }

    private fun updateResourcesLocale(context: Context, locale: Locale): Context? {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}
package com.traday.longholder.di.qualifire

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SubscriptionPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsPreferences
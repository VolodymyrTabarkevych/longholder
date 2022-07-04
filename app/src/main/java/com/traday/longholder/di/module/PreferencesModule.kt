package com.traday.longholder.di.module

import com.traday.longholder.data.local.preferences.subscription.ISubscriptionPreferences
import com.traday.longholder.data.local.preferences.subscription.SubscriptionPreferences
import com.traday.longholder.data.local.preferences.user.IUserPreferences
import com.traday.longholder.data.local.preferences.user.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface PreferencesModule {

    @Binds
    fun provideUserPreferences(preferences: UserPreferences): IUserPreferences

    @Binds
    fun provideSubscriptionPreferences(preferences: SubscriptionPreferences): ISubscriptionPreferences
}
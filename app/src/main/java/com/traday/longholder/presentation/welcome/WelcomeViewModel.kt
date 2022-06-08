package com.traday.longholder.presentation.welcome

import androidx.lifecycle.SavedStateHandle
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.usecase.LogoutUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    state: SavedStateHandle
) : BaseViewModel() {

    private val needLogout = WelcomeFragmentArgs.fromSavedStateHandle(state).needLogout

    fun checkIfLogoutNeeded() {
        if (needLogout) {
            executeUseCase(logoutUseCase, EmptyParams())
        }
    }
}
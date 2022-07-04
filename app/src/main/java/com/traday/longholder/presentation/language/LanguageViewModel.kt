package com.traday.longholder.presentation.language

import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.domain.usecase.SetLanguageUseCase
import com.traday.longholder.domain.usecase.SubscribeOnSelectedLanguageUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val setLanguageUseCase: SetLanguageUseCase,
    subscribeOnSelectedLanguageUseCase: SubscribeOnSelectedLanguageUseCase
) : BaseViewModel() {

    val selectedLanguageLiveData =
        executeUseCase(subscribeOnSelectedLanguageUseCase, EmptyParams()).asLiveData()

    fun setLanguage(language: Language) {
        executeUseCase(setLanguageUseCase, SetLanguageUseCase.Params(language))
    }
}
package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ISettingsRepository
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val settingsRepository: ISettingsRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<SetLanguageUseCase.Params, Language>() {

    override suspend fun run(params: Params): Resource<Language> {
        return settingsRepository.setSelectedLanguage(params.language).toResource(errorHandler)
    }

    class Params(val language: Language) : EmptyParams()
}
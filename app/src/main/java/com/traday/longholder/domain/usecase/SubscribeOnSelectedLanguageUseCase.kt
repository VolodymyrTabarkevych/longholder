package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.FlowUseCase
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeOnSelectedLanguageUseCase @Inject constructor(
    private val settingsRepository: ISettingsRepository,
    private val errorHandler: IErrorHandler
) : FlowUseCase<EmptyParams, Language>() {

    override fun run(params: EmptyParams): Flow<Resource<Language>> {
        return settingsRepository.subscribeOnSelectedLanguage().toResource(errorHandler)
    }
}
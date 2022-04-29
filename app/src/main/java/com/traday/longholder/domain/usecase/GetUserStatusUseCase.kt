package com.traday.longholder.domain.usecase

import com.traday.longholder.data.base.Result
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.repository.IUserRepository
import javax.inject.Inject

class GetUserStatusUseCase @Inject constructor(
    private val userRepository: IUserRepository
) : BaseUseCase<EmptyParams, UserStatus>() {

    override suspend fun run(params: EmptyParams): Resource<UserStatus> {
        val userTokenResult = userRepository.getUserToken()
        val isOnboardingPassedResult = userRepository.isOnboardingPassed()
        if (userTokenResult !is Result.Success) return Resource.Success(UserStatus.NOT_AUTHORIZED)
        if (isOnboardingPassedResult !is Result.Success) return Resource.Success(UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING)
        return if (isOnboardingPassedResult.data) {
            Resource.Success(UserStatus.AUTHORIZED)
        } else {
            Resource.Success(UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING)
        }
    }
}
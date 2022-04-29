package com.traday.longholder.remote.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.remote.dto.UserDto

interface IUserRemoteDataSource {

    suspend fun getUser(): Result<UserDto>
}
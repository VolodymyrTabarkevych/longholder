package com.traday.longholder.data.remote.datasource.user

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.UserDto

interface IUserRemoteDataSource {

    suspend fun getUser(): Result<UserDto>
}
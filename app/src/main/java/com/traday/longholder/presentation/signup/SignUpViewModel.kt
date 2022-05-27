package com.traday.longholder.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.User
import com.traday.longholder.domain.usecase.LoginUseCase
import com.traday.longholder.domain.usecase.RegisterUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : BaseValidationViewModel() {

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData

    private val _registerLiveData = MutableLiveData<Resource<Unit>>()
    val registerLiveData: LiveData<Resource<Unit>> get() = _registerLiveData

    private val _loginLiveData = MutableLiveData<Resource<User>>()
    val loginLiveData: LiveData<Resource<User>> get() = _loginLiveData

    fun validateData(email: String, password: String, confirmPassword: String) {
        onValidateFields(
            ::onValidationSuccess,
            CredentialValidator.Email.validate(email),
            CredentialValidator.Password.validate(password),
            CredentialValidator.PasswordMatch.validate(password, confirmPassword)
        )
    }

    private fun onValidationSuccess() {
        _buttonStateLiveData.postValue(true)
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        super.onValidateError(errorList)
        _buttonStateLiveData.postValue(false)
    }

    fun register(email: String, password: String, confirmPassword: String) {
        executeUseCase(
            registerUseCase,
            RegisterUseCase.Params(email = email, password = password)
        ) {
            _registerLiveData.postValue(it)
        }
    }

    fun login(email: String, password: String) {
        executeUseCase(loginUseCase, LoginUseCase.Params(email = email, password = password)) {
            _loginLiveData.postValue(it)
        }
    }
}
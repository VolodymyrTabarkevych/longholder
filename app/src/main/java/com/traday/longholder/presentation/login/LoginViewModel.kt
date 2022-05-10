package com.traday.longholder.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.User
import com.traday.longholder.domain.usecase.LoginUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseValidationViewModel() {

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData

    private val _loginLiveData = MutableLiveData<Resource<User>>()
    val loginLiveData: LiveData<Resource<User>> get() = _loginLiveData

    fun validateFields(email: String, password: String) {
        onValidateFields(
            ::onValidationSuccess,
            CredentialValidator.Email.validate(email),
            CredentialValidator.Password.validate(password)
        )
    }

    private fun onValidationSuccess() {
        _buttonStateLiveData.postValue(true)
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        super.onValidateError(errorList)
        _buttonStateLiveData.postValue(false)
    }

    fun login(email: String, password: String) {
        executeUseCase(
            useCase = loginUseCase,
            showDialogOnError = false,
            params = LoginUseCase.Params(email, password)
        ) {
            _loginLiveData.postValue(it)
        }
    }
}
package com.traday.longholder.presentation.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.usecase.ForgotPasswordUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseValidationViewModel() {

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData

    private val _forgotPasswordLiveData = MutableLiveData<Resource<Unit>>()
    val forgotPasswordLiveData: LiveData<Resource<Unit>> get() = _forgotPasswordLiveData

    fun validateFields(email: String) {
        onValidateFields(::onValidationSuccess, CredentialValidator.Email.validate(email))
    }

    private fun onValidationSuccess() {
        _buttonStateLiveData.postValue(true)
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        super.onValidateError(errorList)
        _buttonStateLiveData.postValue(false)
    }

    fun forgotPassword(email: String) {
        executeUseCase(forgotPasswordUseCase, ForgotPasswordUseCase.Params(email)) {
            _forgotPasswordLiveData.postValue(it)
        }
    }
}
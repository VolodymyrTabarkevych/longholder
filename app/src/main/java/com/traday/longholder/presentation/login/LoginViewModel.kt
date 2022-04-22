package com.traday.longholder.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class LoginViewModel : BaseValidationViewModel() {

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData


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
}
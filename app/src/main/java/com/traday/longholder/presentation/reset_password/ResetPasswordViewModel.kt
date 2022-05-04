package com.traday.longholder.presentation.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class ResetPasswordViewModel : BaseValidationViewModel() {

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData


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
}
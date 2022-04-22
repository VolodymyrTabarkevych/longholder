package com.traday.longholder.presentation.base

import androidx.lifecycle.LiveData
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.presentation.validation.validator.base.ValidateResultHandler

abstract class BaseValidationViewModel : BaseViewModel(), ValidateResultHandler {

    private val _validationErrorsLiveData = EventLiveData<List<ValidateResult.Error>>()
    val validationErrorsLiveData: LiveData<List<ValidateResult.Error>> = _validationErrorsLiveData

    override fun onValidateFields(
        onValidateSuccess: () -> Unit,
        vararg validateResult: ValidateResult?
    ) {
        val errorList = validateResult.mapNotNull { if (it is ValidateResult.Error) it else null }
        if (errorList.isEmpty()) {
            onValidateSuccess.invoke()
        } else {
            onValidateError(errorList)
        }
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        _validationErrorsLiveData.postValue(errorList)
    }
}
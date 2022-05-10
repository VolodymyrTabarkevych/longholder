package com.traday.longholder.presentation.calculator

import androidx.lifecycle.LiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.usecase.AddPercentUseCase
import com.traday.longholder.domain.usecase.NumberFromUseCase
import com.traday.longholder.domain.usecase.PercentFromUseCase
import com.traday.longholder.domain.usecase.SubtractPercentUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.base.EventLiveData
import com.traday.longholder.presentation.validation.validator.CalcValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val percentFromUseCase: PercentFromUseCase,
    private val numberFromUseCase: NumberFromUseCase,
    private val addPercentUseCase: AddPercentUseCase,
    private val subtractPercentUseCase: SubtractPercentUseCase
) : BaseValidationViewModel() {

    private val _percentFromLiveData = EventLiveData<Resource<String>>()
    val percentFromLiveData: LiveData<Resource<String>> get() = _percentFromLiveData

    private val _numberFromLiveData = EventLiveData<Resource<String>>()
    val numberFromLiveData: LiveData<Resource<String>> get() = _numberFromLiveData

    private val _addPercentLiveData = EventLiveData<Resource<String>>()
    val addPercentLiveData: LiveData<Resource<String>> get() = _addPercentLiveData

    private val _subtractPercentLiveData = EventLiveData<Resource<String>>()
    val subtractPercentLiveData: LiveData<Resource<String>> get() = _subtractPercentLiveData

    fun validatePercentFromAndProceed(percent: Double?, number: Double?) {
        onValidateFields(
            onValidateSuccess = { percentFrom(percent!!, number!!) },
            CalcValidation.FromPercent.validate(percent),
            CalcValidation.FromPercentNumber.validate(number)
        )
    }

    private fun percentFrom(percent: Double, number: Double) {
        executeUseCase(
            percentFromUseCase,
            PercentFromUseCase.Params(percent = percent, number = number)
        ) {
            _percentFromLiveData.postValue(it)
        }
    }

    fun validateNumberFromAndProceed(numberFrom: Double?, numberThat: Double?) {
        onValidateFields(
            onValidateSuccess = { numberFrom(numberFrom!!, numberThat!!) },
            CalcValidation.Number.validate(numberFrom),
            CalcValidation.NumberFrom.validate(numberThat)
        )
    }

    private fun numberFrom(numberFrom: Double, numberThat: Double) {
        executeUseCase(
            numberFromUseCase,
            NumberFromUseCase.Params(numberFrom = numberFrom, numberThat = numberThat)
        ) {
            _numberFromLiveData.postValue(it)
        }
    }

    fun validateAddPercentAndProceed(percent: Double?, number: Double?) {
        onValidateFields(
            onValidateSuccess = { addPercent(percent!!, number!!) },
            CalcValidation.AddPercent.validate(percent),
            CalcValidation.AddPercentNumber.validate(number)
        )
    }

    private fun addPercent(percent: Double, number: Double) {
        executeUseCase(
            addPercentUseCase,
            AddPercentUseCase.Params(percent = percent, number = number)
        ) {
            _addPercentLiveData.postValue(it)
        }
    }

    fun validateSubtractPercentAndProceed(percent: Double?, number: Double?) {
        onValidateFields(
            onValidateSuccess = { subtractPercent(percent!!, number!!) },
            CalcValidation.SubtractPercent.validate(percent),
            CalcValidation.SubtractPercentNumber.validate(number)
        )
    }

    private fun subtractPercent(percent: Double, number: Double) {
        executeUseCase(
            subtractPercentUseCase,
            SubtractPercentUseCase.Params(percent = percent, number = number)
        ) {
            _subtractPercentLiveData.postValue(it)
        }
    }
}
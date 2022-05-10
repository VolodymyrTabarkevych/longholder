package com.traday.longholder.presentation.calculator

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentCalculatorBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.extensions.setupWithErrorClearListener
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.validation.exception.calc.*
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class CalculatorFragment : BaseMVVMFragment<CalculatorViewModel, FragmentCalculatorBinding>(
    layoutResId = R.layout.fragment_calculator,
    tabBarMode = TabBarMode.INVISIBLE
) {

    override val binding: FragmentCalculatorBinding by viewBinding(FragmentCalculatorBinding::bind)

    override val viewModel: CalculatorViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initFieldsListeners()
        initActionButtons()
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilCalculatorFromPercent.setupWithErrorClearListener()
            tilCalculatorFromPercentNumber.setupWithErrorClearListener()
            tilCalculatorNumber.setupWithErrorClearListener()
            tilCalculatorFromNumber.setupWithErrorClearListener()
            tilCalculatorAddPercent.setupWithErrorClearListener()
            tilCalculatorAddPercentNumber.setupWithErrorClearListener()
            tilCalculatorSubtractPercent.setupWithErrorClearListener()
            tilCalculatorSubtractPercentNumber.setupWithErrorClearListener()
        }
    }

    private fun initActionButtons() {
        with(binding) {
            stCalculator.setLeftActionOnCLickListener { navController.popBackStack() }
            tilCalculatorFromPercentNumber.setEndIconOnClickListener {
                val percent = etCalculatorFromPercent.text.toString().toDoubleOrNull()
                val number = etCalculatorFromPercentNumber.text.toString().toDoubleOrNull()
                viewModel.validatePercentFromAndProceed(percent = percent, number = number)
            }
            tilCalculatorFromNumber.setEndIconOnClickListener {
                val numberFrom = etCalculatorNumber.text.toString().toDoubleOrNull()
                val numberThat = etCalculatorFromNumber.text.toString().toDoubleOrNull()
                viewModel.validateNumberFromAndProceed(
                    numberFrom = numberFrom,
                    numberThat = numberThat
                )
            }
            tilCalculatorAddPercentNumber.setEndIconOnClickListener {
                val percent = etCalculatorAddPercent.text.toString().toDoubleOrNull()
                val number = etCalculatorAddPercentNumber.text.toString().toDoubleOrNull()
                viewModel.validateAddPercentAndProceed(percent = percent, number = number)
            }
            tilCalculatorSubtractPercentNumber.setEndIconOnClickListener {
                val percent = etCalculatorSubtractPercent.text.toString().toDoubleOrNull()
                val number = etCalculatorSubtractPercentNumber.text.toString().toDoubleOrNull()
                viewModel.validateSubtractPercentAndProceed(percent = percent, number = number)
            }
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
            percentFromLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setLoading(false)
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        binding.tvCalculatorFromPercentResult.text = it.data
                    }
                }
            }

            numberFromLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setLoading(false)
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        binding.tvCalculatorFromNumberResult.text = it.data
                    }
                }
            }

            addPercentLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setLoading(false)
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        binding.tvCalculatorAddPercentResult.text = it.data
                    }
                }
            }

            subtractPercentLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setLoading(false)
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        binding.tvCalculatorSubtractPercentResult.text = it.data
                    }
                }
            }
        }
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is FromPercentNotValidException -> setPercentFromError(getString(it.exception.stringId))
                is FromPercentNumberNotValidException -> setPercentFromNumberError(getString(it.exception.stringId))
                is NumberNotValidException -> setNumberError(getString(it.exception.stringId))
                is NumberFromNotValidException -> setNumberFromError(getString(it.exception.stringId))
                is AddPercentNotValidException -> setAddPercentError(getString(it.exception.stringId))
                is AddPercentNumberNotValidException -> setAddPercentNumberError(getString(it.exception.stringId))
                is SubtractPercentNotValidException -> setSubtractPercentError(getString(it.exception.stringId))
                is SubtractPercentNumberNotValidException -> setSubtractPercentNumberError(
                    getString(
                        it.exception.stringId
                    )
                )
            }
        }
    }

    private fun setPercentFromError(errorMsg: String?) {
        binding.tilCalculatorFromPercent.error = errorMsg
    }

    private fun setPercentFromNumberError(errorMsg: String?) {
        binding.tilCalculatorFromPercentNumber.error = errorMsg
    }

    private fun setNumberError(errorMsg: String?) {
        binding.tilCalculatorNumber.error = errorMsg
    }

    private fun setNumberFromError(errorMsg: String?) {
        binding.tilCalculatorFromNumber.error = errorMsg
    }

    private fun setAddPercentError(errorMsg: String?) {
        binding.tilCalculatorAddPercent.error = errorMsg
    }

    private fun setAddPercentNumberError(errorMsg: String?) {
        binding.tilCalculatorAddPercentNumber.error = errorMsg
    }

    private fun setSubtractPercentError(errorMsg: String?) {
        binding.tilCalculatorSubtractPercent.error = errorMsg
    }

    private fun setSubtractPercentNumberError(errorMsg: String?) {
        binding.tilCalculatorSubtractPercentNumber.error = errorMsg
    }

    private fun setLoading(isLoading: Boolean) {
        binding.flCalculatorProgressBar.isVisible = isLoading
    }
}
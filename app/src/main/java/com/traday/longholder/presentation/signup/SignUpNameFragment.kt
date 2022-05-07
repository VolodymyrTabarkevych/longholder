package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpNameBinding
import com.traday.longholder.extensions.*
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.NameNotValidException
import com.traday.longholder.presentation.validation.exception.SurenameNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class SignUpNameFragment : BaseMVVMFragment<SignUpViewModel, FragmentSignUpNameBinding>(
    R.layout.fragment_sign_up_name
) {

    override val binding: FragmentSignUpNameBinding by viewBinding(FragmentSignUpNameBinding::bind)

    override val viewModel: SignUpViewModel by activityViewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stSignUpName.setLeftActionOnCLickListener { navController.popBackStack() }
            stSignUpName.setRightActionOnClickListener {
                navController.navigateSafe(
                    SignUpNameFragmentDirections.actionSignUpNameFragmentToSingUpPasswordFragment()
                )
            }
            pbSignUpNameNext.setOnClickListener {
                val name: String = etSignUpName.text.toString()
                val sureName: String = etSignUpSurname.text.toString()
                viewModel.saveName(name + sureName)
                navController.navigateSafe(
                    SignUpNameFragmentDirections.actionSignUpNameFragmentToSingUpPasswordFragment()
                )
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilSignUpName.setupWithErrorClearListener()
            etSignUpName.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etSignUpName.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etSignUpName.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearInputFieldsFocusAndHideKeyboard()
                    return@setOnEditorActionListener false
                }
                return@setOnEditorActionListener true
            }

            tilSignUpSurname.setupWithErrorClearListener()
            etSignUpSurname.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etSignUpSurname.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etSignUpSurname.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearInputFieldsFocusAndHideKeyboard()
                    return@setOnEditorActionListener false
                }
                return@setOnEditorActionListener true
            }
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            fullNameButtonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
        }
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbSignUpNameNext.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is NameNotValidException -> setNameError(getString(it.exception.stringId))
                is SurenameNotValidException -> setSurenameError(getString(it.exception.stringId))
            }
        }
    }

    private fun setNameError(errorMsg: String?) {
        with(binding) {
            if (!etSignUpName.hasFocus() && etSignUpName.text.toString().isNotEmpty()) {
                tilSignUpName.error = errorMsg
            }
        }
    }

    private fun setSurenameError(errorMsg: String?) {
        with(binding) {
            if (!etSignUpSurname.hasFocus() && etSignUpSurname.text.toString().isNotEmpty()) {
                tilSignUpSurname.error = errorMsg
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            val name: String = etSignUpName.text.toString()
            val surname: String = etSignUpName.text.toString()
            viewModel.validateFullName(name = name, surename = surname)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etSignUpName.clearFocus()
            etSignUpSurname.clearFocus()
            hideKeyboard()
        }
    }
}
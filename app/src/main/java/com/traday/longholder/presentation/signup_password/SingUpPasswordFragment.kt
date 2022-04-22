package com.traday.longholder.presentation.signup_password

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.NavMainDirections
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpPasswordBinding
import com.traday.longholder.extensions.*
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class SingUpPasswordFragment :
    BaseMVVMFragment<SingUpPasswordViewModel, FragmentSignUpPasswordBinding>(
        R.layout.fragment_sign_up_password
    ) {

    override val binding: FragmentSignUpPasswordBinding by viewBinding(FragmentSignUpPasswordBinding::bind)

    override val viewModel: SingUpPasswordViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stSignUpPassword.setLeftActionOnCLickListener { navController.popBackStack() }
            pbSignUpPasswordNext.setOnClickListener {
                pbSignUpPasswordNext.setLoading(true)
                mainHandler.postDelayed({
                    pbSignUpPasswordNext.setLoading(false)
                    navController.navigateSafe(NavMainDirections.actionGlobalOnboardingFragment())
                }, 2000)
            }
            pbSignUpPasswordNext.setClickListenerForDisabledState {
                clearInputFieldsFocusAndHideKeyboard()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilSignUpPasswrod.setupWithErrorClearListener()
            etSignUpPassword.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etSignUpPassword.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etSignUpPassword.setOnEditorActionListener { _, actionId, _ ->
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
            buttonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
        }
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbSignUpPasswordNext.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is PasswordNotValidException -> setPasswordError(getString(it.exception.stringId))
            }
        }
    }

    private fun setPasswordError(errorMsg: String?) {
        with(binding) {
            if (!etSignUpPassword.hasFocus() && etSignUpPassword.text.toString().isNotEmpty()) {
                tilSignUpPasswrod.error = errorMsg
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            val password: String = etSignUpPassword.text.toString()
            viewModel.validateFields(password)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etSignUpPassword.clearFocus()
            hideKeyboard()
        }
    }
}
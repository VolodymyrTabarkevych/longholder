package com.traday.longholder.presentation.reset_password

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentResetPasswordBinding
import com.traday.longholder.extensions.OnTextChangedListener
import com.traday.longholder.extensions.hideKeyboard
import com.traday.longholder.extensions.setOnTextChangedListener
import com.traday.longholder.extensions.setupWithErrorClearListener
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class ResetPasswordFragment :
    BaseMVVMFragment<ResetPasswordViewModel, FragmentResetPasswordBinding>(
        R.layout.fragment_reset_password
    ) {

    override val binding: FragmentResetPasswordBinding by viewBinding(FragmentResetPasswordBinding::bind)

    override val viewModel: ResetPasswordViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stResetPassword.setLeftActionOnCLickListener { navController.popBackStack() }
            pbResetPassword.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilResetPasswordEmail.setupWithErrorClearListener()
            tilResetPasswordEmail.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etResetPasswordEmail.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etResetPasswordEmail.setOnEditorActionListener { _, actionId, _ ->
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
        binding.pbResetPassword.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is EmailNotValidException -> setEmailError(getString(it.exception.stringId))
            }
        }
    }

    private fun setEmailError(errorMsg: String?) {
        with(binding) {
            if (!etResetPasswordEmail.hasFocus() && etResetPasswordEmail.text.toString()
                    .isNotEmpty()
            ) {
                tilResetPasswordEmail.error = errorMsg
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            val email: String = etResetPasswordEmail.text.toString()
            viewModel.validateFields(email)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etResetPasswordEmail.clearFocus()
            hideKeyboard()
        }
    }
}
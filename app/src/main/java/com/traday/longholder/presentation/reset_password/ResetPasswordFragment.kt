package com.traday.longholder.presentation.reset_password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentResetPasswordBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.entities.BaseError
import com.traday.longholder.extensions.hideKeyboard
import com.traday.longholder.extensions.setErrorIfOnFocusAndNotEmpty
import com.traday.longholder.extensions.setupWithDefaultConfiguration
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.utils.showDialog

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
                val email: String = binding.etResetPasswordEmail.text.toString()
                viewModel.forgotPassword(email)
            }
            pbResetPassword.setClickListenerForDisabledState {
                clearInputFieldsFocusAndHideKeyboard()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilResetPasswordEmail.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            buttonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
            forgotPasswordLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setForgotPasswordLoading(false)
                        clearInputFieldsFocusAndHideKeyboard()
                        if (it.error is BaseError.NoUserAssociatedWithEmailError) {
                            setEmailError(it.error.message.toString(resources))
                        }
                    }
                    is Resource.Loading -> setForgotPasswordLoading(true)
                    is Resource.Success -> {
                        setForgotPasswordLoading(false)
                        val email: String = binding.etResetPasswordEmail.text.toString()
                        showDialog(
                            title = getString(R.string.reset_password_mail_has_been_sent, email),
                            onPositiveButtonClicked = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }

    private fun setForgotPasswordLoading(isLoading: Boolean) {
        binding.pbResetPassword.setLoading(isLoading)
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
        binding.tilResetPasswordEmail.setErrorIfOnFocusAndNotEmpty(errorMsg)
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
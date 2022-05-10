package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpEmailBinding
import com.traday.longholder.extensions.hideKeyboard
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.setErrorIfOnFocusAndNotEmpty
import com.traday.longholder.extensions.setupWithDefaultConfiguration
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class SignUpEmailFragment : BaseMVVMFragment<SignUpViewModel, FragmentSignUpEmailBinding>(
    R.layout.fragment_sign_up_email
) {

    override val binding: FragmentSignUpEmailBinding by viewBinding(FragmentSignUpEmailBinding::bind)

    override val viewModel: SignUpViewModel by activityViewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stSignUpEmail.setLeftActionOnCLickListener { navController.popBackStack() }
            pbSignUpEmailNext.setOnClickListener {
                val email: String = etSignUpEmail.text.toString()
                viewModel.saveEmail(email)
                navController.navigateSafe(
                    SignUpEmailFragmentDirections.actionSignUpFragmentToSignUpNameFragment()
                )
            }
            pbSignUpEmailNext.setClickListenerForDisabledState {
                clearInputFieldsFocusAndHideKeyboard()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilSignUpEmail.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            emailButtonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
        }
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbSignUpEmailNext.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is EmailNotValidException -> setEmailError(getString(it.exception.stringId))
            }
        }
    }

    private fun setEmailError(errorMsg: String?) {
        binding.tilSignUpEmail.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun validateFields() {
        with(binding) {
            val email: String = etSignUpEmail.text.toString()
            viewModel.validateEmail(email)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etSignUpEmail.clearFocus()
            hideKeyboard()
        }
    }
}
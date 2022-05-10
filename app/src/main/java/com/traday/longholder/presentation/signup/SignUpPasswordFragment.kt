package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpPasswordBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.extensions.hideKeyboard
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.setErrorIfOnFocusAndNotEmpty
import com.traday.longholder.extensions.setupWithDefaultConfiguration
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.PasswordMatchException
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class SignUpPasswordFragment : BaseMVVMFragment<SignUpViewModel, FragmentSignUpPasswordBinding>(
    R.layout.fragment_sign_up_password
) {

    override val binding: FragmentSignUpPasswordBinding by viewBinding(FragmentSignUpPasswordBinding::bind)

    override val viewModel: SignUpViewModel by activityViewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stSignUpPassword.setLeftActionOnCLickListener { navController.popBackStack() }
            pbSignUpPasswordNext.setOnClickListener {
                val password: String = etSignUpPassword.text.toString()
                viewModel.register(password)
            }
            pbSignUpPasswordNext.setClickListenerForDisabledState {
                clearInputFieldsFocusAndHideKeyboard()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilSignUpPasswrod.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
            tilSignUpConfirmPasswrod.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            passwordButtonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
            registerLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setRegisterLoading(false)
                    is Resource.Loading -> setRegisterLoading(true)
                    is Resource.Success -> {
                        val password: String = binding.etSignUpPassword.text.toString()
                        viewModel.login(password)
                    }
                }
            }
            loginLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setRegisterLoading(false)
                    is Resource.Loading -> setRegisterLoading(true)
                    is Resource.Success -> {
                        navController.navigateSafe(SignUpPasswordFragmentDirections.actionSingUpPasswordFragmentToOnboardingFragment())
                    }
                }
            }
        }
    }

    private fun setRegisterLoading(isLoading: Boolean) {
        binding.pbSignUpPasswordNext.setLoading(isLoading)
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbSignUpPasswordNext.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is PasswordNotValidException -> setPasswordError(getString(it.exception.stringId))
                is PasswordMatchException -> setConfirmPasswordError(getString(it.exception.stringId))
            }
        }
    }

    private fun setPasswordError(errorMsg: String?) {
        binding.tilSignUpPasswrod.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun setConfirmPasswordError(errorMsg: String?) {
        binding.tilSignUpConfirmPasswrod.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun validateFields() {
        with(binding) {
            val password: String = etSignUpPassword.text.toString()
            val confirmPassword: String = etSignUpConfirmPassword.text.toString()
            viewModel.validatePassword(password, confirmPassword)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etSignUpPassword.clearFocus()
            etSignUpConfirmPassword.clearFocus()
            hideKeyboard()
        }
    }
}
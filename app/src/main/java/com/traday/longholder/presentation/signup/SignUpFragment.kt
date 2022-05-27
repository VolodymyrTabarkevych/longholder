package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.entities.BaseError
import com.traday.longholder.extensions.hideKeyboard
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.setErrorIfOnFocusAndNotEmpty
import com.traday.longholder.extensions.setupWithDefaultConfiguration
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.exception.PasswordMatchException
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class SignUpFragment : BaseMVVMFragment<SignUpViewModel, FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {

    override val binding: FragmentSignUpBinding by viewBinding(FragmentSignUpBinding::bind)

    override val viewModel: SignUpViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stSignUp.setLeftActionOnCLickListener { navController.popBackStack() }
            pbSignUpNext.setOnClickListener {
                val email: String = etSignUpEmail.text.toString()
                val password: String = etSignUpPassword.text.toString()
                val confirmPassword: String = etSignUpConfirmPassword.text.toString()
                viewModel.register(email, password, confirmPassword)
            }
            pbSignUpNext.setClickListenerForDisabledState {
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
            tilSignUpPassword.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
            tilSignUpConfirmPassword.setupWithDefaultConfiguration(
                onStateChanged = ::validateFields,
                onActionDone = ::clearInputFieldsFocusAndHideKeyboard
            )
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            buttonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
            registerLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setRegisterLoading(false)
                        clearInputFieldsFocusAndHideKeyboard()
                        if (it.error is BaseError.UserAlreadyExistsError) {
                            it.error.stringResId?.let { stringResId ->
                                setEmailError(getString(stringResId))
                            }
                        }
                    }
                    is Resource.Loading -> setRegisterLoading(true)
                    is Resource.Success -> {
                        with(binding) {
                            val email: String = etSignUpEmail.text.toString()
                            val password: String = etSignUpPassword.text.toString()
                            viewModel.login(email, password)
                        }
                    }
                }
            }
            loginLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setRegisterLoading(false)
                    is Resource.Loading -> setRegisterLoading(true)
                    is Resource.Success -> {
                        navController.navigateSafe(SignUpFragmentDirections.actionSignUpFragmentToOnboardingFragment())
                    }
                }
            }
        }
    }

    private fun setRegisterLoading(isLoading: Boolean) {
        binding.pbSignUpNext.setLoading(isLoading)
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbSignUpNext.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is EmailNotValidException -> setEmailError(getString(it.exception.stringId))
                is PasswordNotValidException -> setPasswordError(getString(it.exception.stringId))
                is PasswordMatchException -> setConfirmPasswordError(getString(it.exception.stringId))
            }
        }
    }

    private fun setEmailError(errorMsg: String?) {
        binding.tilSignUpEmail.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun setPasswordError(errorMsg: String?) {
        binding.tilSignUpPassword.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun setConfirmPasswordError(errorMsg: String?) {
        binding.tilSignUpConfirmPassword.setErrorIfOnFocusAndNotEmpty(errorMsg)
    }

    private fun validateFields() {
        with(binding) {
            val email: String = etSignUpEmail.text.toString()
            val password: String = etSignUpPassword.text.toString()
            val confirmPassword: String = etSignUpConfirmPassword.text.toString()
            viewModel.validateData(email, password, confirmPassword)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etSignUpEmail.clearFocus()
            etSignUpPassword.clearFocus()
            etSignUpConfirmPassword.clearFocus()
            hideKeyboard()
        }
    }
}
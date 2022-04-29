package com.traday.longholder.presentation.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentLoginBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.extensions.*
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.exception.EmailNotValidException
import com.traday.longholder.presentation.validation.exception.PasswordNotValidException
import com.traday.longholder.presentation.validation.validator.base.ValidateResult

class LoginFragment : BaseMVVMFragment<LoginViewModel, FragmentLoginBinding>(
    R.layout.fragment_login
) {

    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override val viewModel: LoginViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initFieldsListeners()
    }

    private fun initActionButtons() {
        with(binding) {
            stLogin.setLeftActionOnCLickListener { navController.popBackStack() }
            pbLogin.setOnClickListener {
                val userName = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                viewModel.login(userName, password)
            }
            pbLogin.setClickListenerForDisabledState {
                clearInputFieldsFocusAndHideKeyboard()
            }
        }
    }

    private fun initFieldsListeners() {
        with(binding) {
            tilLoginEmail.setupWithErrorClearListener()
            etLoginEmail.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etLoginEmail.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })

            tilLoginPassword.setupWithErrorClearListener()
            etLoginPassword.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etLoginPassword.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etLoginPassword.setOnEditorActionListener { _, actionId, _ ->
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
            loginLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setLoginLoading(false)
                        clearInputFieldsFocusAndHideKeyboard()
                        setEmailError(getString(R.string.validation_email_not_valid))
                        setPasswordError(getString(R.string.validation_password_not_valid))
                    }
                    is Resource.Loading -> setLoginLoading(true)
                    is Resource.Success -> {
                        setLoginLoading(false)
                        navController.navigateSafe(LoginFragmentDirections.actionGlobalOnboardingFragment())
                    }
                }
            }
            buttonStateLiveData.observe(viewLifecycleOwner, ::changeButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::showValidationError)
        }
    }

    private fun setLoginLoading(isLoading: Boolean) {
        binding.pbLogin.setLoading(isLoading)
    }

    private fun changeButtonState(enabled: Boolean) {
        binding.pbLogin.isEnabled = enabled
    }

    private fun showValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
            when (it.exception) {
                is EmailNotValidException -> setEmailError(getString(it.exception.stringId))
                is PasswordNotValidException -> setPasswordError(getString(it.exception.stringId))
            }
        }
    }

    private fun setEmailError(errorMsg: String?) {
        with(binding) {
            if (!etLoginEmail.hasFocus() && etLoginEmail.text.toString().isNotEmpty()) {
                tilLoginEmail.error = errorMsg
            }
        }
    }

    private fun setPasswordError(errorMsg: String?) {
        with(binding) {
            if (!etLoginPassword.hasFocus() && etLoginPassword.text.toString().isNotEmpty()) {
                tilLoginPassword.error = errorMsg
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            val email: String = etLoginEmail.text.toString()
            val password: String = etLoginPassword.text.toString()
            viewModel.validateFields(email, password)
        }
    }

    private fun clearInputFieldsFocusAndHideKeyboard() {
        with(binding) {
            etLoginEmail.clearFocus()
            etLoginPassword.clearFocus()
            hideKeyboard()
        }
    }
}
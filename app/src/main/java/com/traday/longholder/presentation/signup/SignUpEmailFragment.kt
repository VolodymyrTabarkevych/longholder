package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpEmailBinding
import com.traday.longholder.extensions.*
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
            tilSignUpEmail.setupWithErrorClearListener()
            etSignUpEmail.setOnFocusChangeListener { _, _ ->
                validateFields()
            }
            etSignUpEmail.setOnTextChangedListener(object : OnTextChangedListener {
                override fun onTextChanged(viewId: Int?) {
                    validateFields()
                }
            })
            etSignUpEmail.setOnEditorActionListener { _, actionId, _ ->
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
        with(binding) {
            if (!etSignUpEmail.hasFocus() && etSignUpEmail.text.toString().isNotEmpty()) {
                tilSignUpEmail.error = errorMsg
            }
        }
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
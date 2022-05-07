package com.traday.longholder.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.User
import com.traday.longholder.domain.usecase.LoginUseCase
import com.traday.longholder.domain.usecase.RegisterUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.exception.*
import com.traday.longholder.presentation.validation.validator.CredentialValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : BaseValidationViewModel() {

    private val _emailButtonStateLiveData = MutableLiveData<Boolean>()
    val emailButtonStateLiveData: LiveData<Boolean> get() = _emailButtonStateLiveData

    private val _fullNameButtonStateLiveData = MutableLiveData<Boolean>()
    val fullNameButtonStateLiveData: LiveData<Boolean> get() = _fullNameButtonStateLiveData

    private val _passwordButtonStateLiveData = MutableLiveData<Boolean>()
    val passwordButtonStateLiveData: LiveData<Boolean> get() = _passwordButtonStateLiveData

    private val _registerLiveData = MutableLiveData<Resource<Unit>>()
    val registerLiveData: LiveData<Resource<Unit>> get() = _registerLiveData

    private val _loginLiveData = MutableLiveData<Resource<User>>()
    val loginLiveData: LiveData<Resource<User>> get() = _loginLiveData

    private lateinit var email: String
    private lateinit var name: String

    fun validateEmail(email: String) {
        onValidateFields(::onEmailValidationSuccess, CredentialValidator.Email.validate(email))
    }

    fun validateFullName(name: String, surename: String) {
        onValidateFields(
            ::onFullNameValidationSuccess,
            CredentialValidator.Name.validate(name),
            CredentialValidator.Surename.validate(surename)
        )
    }

    fun validatePassword(password: String, confirmPassword: String) {
        onValidateFields(
            ::onPasswordValidationSuccess,
            CredentialValidator.Password.validate(password),
            CredentialValidator.PasswordMatch.validate(password, confirmPassword)
        )
    }

    private fun onEmailValidationSuccess() {
        _emailButtonStateLiveData.postValue(true)
    }

    private fun onFullNameValidationSuccess() {
        _fullNameButtonStateLiveData.postValue(true)
    }

    private fun onPasswordValidationSuccess() {
        _passwordButtonStateLiveData.postValue(true)
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        super.onValidateError(errorList)
        errorList.forEach {
            when (it.exception) {
                is EmailNotValidException -> _emailButtonStateLiveData.postValue(false)
                is NameNotValidException -> _fullNameButtonStateLiveData.postValue(false)
                is SurenameNotValidException -> _fullNameButtonStateLiveData.postValue(false)
                is PasswordNotValidException -> _passwordButtonStateLiveData.postValue(false)
                is PasswordMatchException -> _passwordButtonStateLiveData.postValue(false)
            }
        }
    }

    fun saveEmail(email: String) {
        this.email = email
    }

    fun saveName(name: String) {
        this.name = name
    }

    fun register(password: String) {
        executeUseCase(
            registerUseCase,
            RegisterUseCase.Params(userName = name, email = email, password = password)
        ) {
            _registerLiveData.postValue(it)
        }
    }

    fun login(password: String) {
        executeUseCase(loginUseCase, LoginUseCase.Params(userName = name, password)) {
            _loginLiveData.postValue(it)
        }
    }
}
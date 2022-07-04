package com.traday.longholder.presentation.language

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentLanguageBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.extensions.getDrawableCompat
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.WindowBackgroundMode

class LanguageFragment : BaseMVVMFragment<LanguageViewModel, FragmentLanguageBinding>(
    layoutResId = R.layout.fragment_language,
    windowBackgroundMode = WindowBackgroundMode.Secondary
) {

    override val binding: FragmentLanguageBinding by viewBinding(FragmentLanguageBinding::bind)

    override val viewModel: LanguageViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        with(binding) {
            stLanguage.setLeftActionOnCLickListener { navController.popBackStack() }
            flLanguageUkrainian.setOnClickListener {
                viewModel.setLanguage(Language.UKRAINIAN)
            }
            flLanguageEnglish.setOnClickListener {
                viewModel.setLanguage(Language.ENGLISH)
            }
        }
    }

    override fun initViewModel() {
        viewModel.selectedLanguageLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Success) setSelectedLanguage(it.data)
        }
    }

    private fun setSelectedLanguage(language: Language) {
        with(binding) {
            when (language) {
                Language.ENGLISH -> {
                    tvLanguageEnglish.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        getDrawableCompat(R.drawable.ic_radio_button_checked),
                        null
                    )
                    tvLanguageUkrainian.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        getDrawableCompat(R.drawable.ic_radio_button_unchecked),
                        null
                    )
                }
                Language.UKRAINIAN -> {
                    tvLanguageEnglish.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        getDrawableCompat(R.drawable.ic_radio_button_unchecked),
                        null
                    )
                    tvLanguageUkrainian.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        getDrawableCompat(R.drawable.ic_radio_button_checked),
                        null
                    )
                }
            }
        }
    }
}
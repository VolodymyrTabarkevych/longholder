package com.traday.longholder.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentProfileBinding
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.base.WindowBackgroundMode

class ProfileFragment : BaseMVVMFragment<ProfileViewModel, FragmentProfileBinding>(
    layoutResId = R.layout.fragment_profile,
    windowBackgroundMode = WindowBackgroundMode.Secondary,
    tabBarMode = TabBarMode.VISIBLE
) {

    override val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override val viewModel: ProfileViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        with(binding) {
            flProfileNotifications.setOnClickListener {
                navController.navigateSafe(
                    ProfileFragmentDirections.actionProfileFragmentToNotificationsFragment()
                )
            }
            flProfileLanguage.setOnClickListener {
                navController.navigateSafe(
                    ProfileFragmentDirections.actionProfileFragmentToLanguageFragment()
                )
            }
            flProfileTheme.setOnClickListener {}
            flProfileSubscription.setOnClickListener {}
            flProfileLogout.setOnClickListener {}
        }
    }

    override fun initViewModel() {}
}
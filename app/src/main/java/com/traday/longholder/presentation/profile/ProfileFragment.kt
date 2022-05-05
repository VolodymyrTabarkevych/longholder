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
import com.traday.longholder.utils.showDialog

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
            flProfileTheme.setOnClickListener {
                navController.navigateSafe(
                    ProfileFragmentDirections.actionProfileFragmentToThemeFragment()
                )
            }
            flProfileSubscription.setOnClickListener {
                navController.navigateSafe(ProfileFragmentDirections.actionProfileFragmentToSubscriptionFragment())
            }
            flProfileLogout.setOnClickListener {
                showDialog(
                    title = getString(R.string.dialog_log_out_title),
                    message = getString(R.string.dialog_if_you_stop_holding),
                    positiveButtonText = getString(R.string.dialog_log_out),
                    onPositiveButtonClicked = { viewModel.logout() },
                    negativeButtonText = getString(R.string.common_cancel)
                )
            }
        }
    }

    override fun initViewModel() {}
}
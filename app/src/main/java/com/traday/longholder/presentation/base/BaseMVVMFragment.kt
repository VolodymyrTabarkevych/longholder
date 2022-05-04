package com.traday.longholder.presentation.base

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.traday.longholder.NavMainDirections
import com.traday.longholder.R
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.showDialog

abstract class BaseMVVMFragment<out VM : BaseViewModel, out B : ViewBinding>(
    @LayoutRes layoutResId: Int,
    windowBackgroundMode: WindowBackgroundMode = WindowBackgroundMode.Primary,
    tabBarMode: TabBarMode = TabBarMode.INVISIBLE
) : BaseFragment<B>(layoutResId, windowBackgroundMode, tabBarMode) {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.unauthorizedLiveData.observe(viewLifecycleOwner) {
            findNavController().navigateSafe(NavMainDirections.actionGlobalNavStart(true))
        }
        viewModel.noInternetConnectionLiveData.observe(viewLifecycleOwner) {
            showDialog(
                title = getString(R.string.error_no_internet_connection),
                message = getString(R.string.error_make_sure_device_is_connected),
                onCustomize = { dialogCommonBinding, _ ->
                    dialogCommonBinding.tvDialogTitle.gravity = Gravity.CENTER
                    dialogCommonBinding.tvDialogMessage.gravity = Gravity.CENTER
                }
            )
        }
    }
}
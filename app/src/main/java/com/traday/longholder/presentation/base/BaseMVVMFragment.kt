package com.traday.longholder.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.traday.longholder.NavMainDirections
import com.traday.longholder.R
import com.traday.longholder.domain.error.entities.BaseError
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.utils.AlertDialogProvider
import com.traday.longholder.utils.showDialog

abstract class BaseMVVMFragment<out VM : BaseViewModel, out B : ViewBinding>(
    @LayoutRes layoutResId: Int,
    windowBackgroundMode: WindowBackgroundMode = WindowBackgroundMode.Primary,
    tabBarMode: TabBarMode = TabBarMode.INVISIBLE
) : BaseFragment<B>(layoutResId, windowBackgroundMode, tabBarMode), AlertDialogProvider {

    protected abstract val viewModel: VM

    private var startDestinationHandler: StartDestinationHandler? = null

    override val alertDialogContext: Context get() = requireContext()

    override var isAlertDialogShowing: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        startDestinationHandler = activity as? StartDestinationHandler
            ?: throw IllegalStateException("Activity ($activity) must implement ${StartDestinationHandler::class.java.name}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.unauthorizedLiveData.observe(viewLifecycleOwner) {
            startDestinationHandler?.setStartDestinationForNestedGraph(
                graphId = R.id.nav_start,
                destinationId = R.id.welcomeFragment
            )
            findNavController().navigateSafe(NavMainDirections.actionGlobalNavStart(true))
        }
        viewModel.showAlertDialogErrorLiveData.observe(viewLifecycleOwner) {
            if (it.error is BaseError.SomethingWentWrongError) {
                showDialog(it.error.message.toString(resources))
            }
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
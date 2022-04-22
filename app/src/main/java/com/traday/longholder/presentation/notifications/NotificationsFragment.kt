package com.traday.longholder.presentation.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentNotificationsBinding
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.WindowBackgroundMode
import com.traday.longholder.presentation.notifications.adapter.NotificationMarginDecoration
import com.traday.longholder.presentation.notifications.adapter.NotificationsAdapter

class NotificationsFragment :
    BaseMVVMFragment<NotificationsViewModel, FragmentNotificationsBinding>(
        layoutResId = R.layout.fragment_notifications,
        windowBackgroundMode = WindowBackgroundMode.Secondary
    ) {

    override val binding: FragmentNotificationsBinding by viewBinding(FragmentNotificationsBinding::bind)

    override val viewModel: NotificationsViewModel by viewModels()

    private val notificationsAdapter by lazy { NotificationsAdapter() }

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initNotificationsRecycler()
    }

    private fun initActionButtons() {
        binding.stNotifications.setLeftActionOnCLickListener { navController.popBackStack() }
    }

    private fun initNotificationsRecycler() {
        binding.rvNotifications.let {
            it.addItemDecoration(NotificationMarginDecoration())
            it.adapter = notificationsAdapter
        }
    }

    override fun initViewModel() {
        val testData = getTestDataForRecycler()
        setNotifications(testData)
    }

    private fun setNotifications(notifications: List<Notification>) {
        notificationsAdapter.submitList(notifications)
    }

    private fun getTestDataForRecycler(): List<Notification> {
        return listOf(
            Notification(
                icon = R.drawable.img_btc,
                cryptoName = "Bitcoin",
                status = "Your holding has ended. You have earned:",
                earned = "+8 323,13 (\$1 225,60)",
                date = "22/02/2022"
            ),
            Notification(
                icon = R.drawable.img_eth,
                cryptoName = "Ethereum",
                status = "Your holding has ended. You have earned:",
                earned = "+1 323,13 (\$225,60)",
                date = "22/02/2022"
            )
        )
    }
}
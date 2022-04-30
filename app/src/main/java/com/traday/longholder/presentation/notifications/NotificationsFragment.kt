package com.traday.longholder.presentation.notifications

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentNotificationsBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.extensions.showDialog
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
        viewModel.getNotificationsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> showDialog(it.error.msg)
                is Resource.Loading -> setNotificationsLoading(true)
                is Resource.Success -> {
                    setNotificationsLoading(false)
                    setNotifications(it.data)
                }
            }
        }
    }

    private fun setNotificationsLoading(isLoading: Boolean) {
        with(binding) {
            pbNotifications.isVisible = isLoading
            rvNotifications.isVisible = !isLoading
        }
    }

    private fun setNotifications(notifications: List<Notification>) {
        notificationsAdapter.submitList(notifications)
    }
}
package com.traday.longholder.presentation.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentWalletBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.StatusBarMode
import com.traday.longholder.presentation.base.TabBarMode

class WalletFragment : BaseMVVMFragment<WalletViewModel, FragmentWalletBinding>(
    layoutResId = R.layout.fragment_wallet,
    statusBarMode = StatusBarMode.Secondary,
    tabBarMode = TabBarMode.VISIBLE
) {

    override val binding: FragmentWalletBinding by viewBinding(FragmentWalletBinding::bind)

    override val viewModel: WalletViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {}

    override fun initViewModel() {}
}
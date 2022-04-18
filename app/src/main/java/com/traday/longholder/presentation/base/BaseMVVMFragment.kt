package com.traday.longholder.presentation.base

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

abstract class BaseMVVMFragment<out VM : BaseViewModel, out B : ViewBinding>(
    @LayoutRes layoutResId: Int,
    tabBarMode: TabBarMode = TabBarMode.INVISIBLE
) : BaseFragment<B>(layoutResId, tabBarMode) {

    protected abstract val viewModel: VM
}
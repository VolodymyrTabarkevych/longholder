package com.traday.longholder.presentation.wallet

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentWalletBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.active.ActiveScreenMode
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.base.WindowBackgroundMode
import com.traday.longholder.presentation.wallet.adapter.ActiveItemViewHolder
import com.traday.longholder.presentation.wallet.adapter.ActivesAdapter
import com.traday.longholder.presentation.wallet.adapter.ActivesMarginDecoration

class WalletFragment : BaseMVVMFragment<WalletViewModel, FragmentWalletBinding>(
    layoutResId = R.layout.fragment_wallet,
    windowBackgroundMode = WindowBackgroundMode.Secondary,
    tabBarMode = TabBarMode.VISIBLE
), ActiveItemViewHolder.EventListener {

    override val binding: FragmentWalletBinding by viewBinding(FragmentWalletBinding::bind)

    override val viewModel: WalletViewModel by viewModels()

    private val activesAdapter by lazy { ActivesAdapter(this) }

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initStartDestination()
        initActionButtons()
        initActivesRecycler()
    }

    private fun initStartDestination() {
        navController.graph.setStartDestination(R.id.nav_wallet)
    }

    private fun initActionButtons() {
        with(binding) {
            pbWalletAddActives.setOnClickListener {
                navController.navigateSafe(
                    WalletFragmentDirections.actionWalletFragmentToActiveFragment(ActiveScreenMode.Create)
                )
            }
            ivWalletAddActives.setOnClickListener {
                navController.navigateSafe(
                    WalletFragmentDirections.actionWalletFragmentToActiveFragment(ActiveScreenMode.Create)
                )
            }
        }
    }

    private fun initActivesRecycler() {
        binding.rvWalletActives.let {
            it.addItemDecoration(ActivesMarginDecoration())
            it.adapter = activesAdapter
        }
    }

    override fun initViewModel() {
        viewModel.getCryptosLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> setCryptosLoading(true)
                is Resource.Loading -> setCryptosLoading(true)
                is Resource.Success -> {
                    setCryptosLoading(false)
                    setCryptos(it.data)
                }
            }
        }
    }

    private fun setCryptosLoading(isLoading: Boolean) {
        with(binding) {
            rvWalletActives.isVisible = !isLoading
            llWalletNoActives.isVisible = !isLoading
            pbWalletActives.isVisible = isLoading
        }
    }

    private fun setCryptos(actives: List<Active>) {
        with(binding) {
            val showActives = actives.isNotEmpty()
            rvWalletActives.isVisible = showActives
            llWalletNoActives.isVisible = !showActives
            handleRecyclerScroll(
                oldListSize = activesAdapter.currentList.size,
                newListSize = actives.size
            )
            activesAdapter.submitList(actives)
        }
    }

    private fun handleRecyclerScroll(oldListSize: Int, newListSize: Int) {
        val needScroll = newListSize > oldListSize
        if (needScroll) {
            mainHandler.postDelayed({
                binding.rvWalletActives.smoothScrollToPosition(FIRST_RECYCLER_POSITION)
            }, RECYCLER_SCROLL_DELAY)
        }
    }

    override fun onActiveClicked(active: Active) {
        navController.navigateSafe(
            WalletFragmentDirections.actionWalletFragmentToActiveFragment(
                ActiveScreenMode.Update(
                    active
                )
            )
        )
    }

    companion object {

        private const val FIRST_RECYCLER_POSITION = 0
        private const val RECYCLER_SCROLL_DELAY = 400L
    }
}
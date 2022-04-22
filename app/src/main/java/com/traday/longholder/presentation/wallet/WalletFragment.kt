package com.traday.longholder.presentation.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentWalletBinding
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.show
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.base.WindowBackgroundMode
import com.traday.longholder.presentation.wallet.adapter.ActiveItemViewHolder
import com.traday.longholder.presentation.wallet.adapter.ActiveMarginDecoration
import com.traday.longholder.presentation.wallet.adapter.ActivesAdapter

class WalletFragment : BaseMVVMFragment<WalletViewModel, FragmentWalletBinding>(
    layoutResId = R.layout.fragment_wallet,
    windowBackgroundMode = WindowBackgroundMode.Secondary,
    tabBarMode = TabBarMode.VISIBLE
), ActiveItemViewHolder.EventListener {

    override val binding: FragmentWalletBinding by viewBinding(FragmentWalletBinding::bind)
    override val viewModel: WalletViewModel by viewModels()
    private val args: WalletFragmentArgs by navArgs()

    private val activesAdapter by lazy { ActivesAdapter(this) }

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initActivesRecycler()
        addNewActiveIfExists(args.active)
    }

    private fun initActionButtons() {
        with(binding) {
            pbWalletAddActives.setOnClickListener {
                navController.navigateSafe(
                    WalletFragmentDirections.actionWalletFragmentToActiveFragment()
                )
            }
            ivWalletAddActives.setOnClickListener {
                navController.navigateSafe(
                    WalletFragmentDirections.actionWalletFragmentToActiveFragment()
                )
            }
        }
    }

    private fun initActivesRecycler() {
        binding.rvWalletActives.let {
            it.addItemDecoration(ActiveMarginDecoration())
            it.adapter = activesAdapter
        }
    }

    override fun initViewModel() {}

    private fun addNewActiveIfExists(active: Active?) {
        with(binding) {
            if (active == null) {
                activesAdapter.submitList(emptyList())
                rvWalletActives.gone()
                tvWalletNoActivesTitle.show()
                tvWalletNoActivesDescription.show()
                pbWalletAddActives.show()
            } else {
                rvWalletActives.show()
                tvWalletNoActivesTitle.gone()
                tvWalletNoActivesDescription.gone()
                pbWalletAddActives.gone()
            }
        }
        activesAdapter.submitList(listOf(active))
    }

    override fun onActiveClicked(active: Active) {
        navController.navigateSafe(
            WalletFragmentDirections.actionWalletFragmentToActiveFragment(
                active
            )
        )
    }
}
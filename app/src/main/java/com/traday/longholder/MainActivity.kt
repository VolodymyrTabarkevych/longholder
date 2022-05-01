package com.traday.longholder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.traday.longholder.databinding.ActivityMainBinding
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.show
import com.traday.longholder.presentation.base.BottomNavigationViewProvider
import com.traday.longholder.presentation.base.TabBarHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), TabBarHandler,
    BottomNavigationViewProvider {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
    private val rootGraph by lazy { navController.navInflater.inflate(R.navigation.nav_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Longholder)
        super.onCreate(savedInstanceState)
        initBottomTabs()
        initViewModel()
    }

    private fun initBottomTabs() {
        binding.bnvMain.setupWithNavController(navController)
    }

    private fun initViewModel() {
        viewModel.userStatus.observe(this, ::handleUserStatus)
    }

    private fun handleUserStatus(userStatus: UserStatus) {
        val destinationId = when (userStatus) {
            UserStatus.NOT_AUTHORIZED -> R.id.welcomeFragment
            UserStatus.NOT_VERIFIED -> TODO()
            UserStatus.AUTHORIZED -> R.id.nav_wallet
            UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING -> R.id.onboardingFragment
        }
        setStartDestination(destinationId)
    }

    private fun setStartDestination(@IdRes destinationId: Int, args: Bundle? = null) {
        rootGraph.setStartDestination(destinationId)
        args?.let {
            navController.setGraph(rootGraph, it)
        } ?: run {
            navController.graph = rootGraph
        }
    }

    override fun showTabs() {
        binding.bnvMain.show()
    }

    override fun hideTabs() {
        binding.bnvMain.gone()
    }

    override fun getBottomNavigationView(): BottomNavigationView {
        return binding.bnvMain
    }

}
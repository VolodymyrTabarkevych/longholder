package com.traday.longholder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.traday.longholder.databinding.ActivityMainBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.extensions.collectLatestWhenStarted
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.show
import com.traday.longholder.presentation.base.BaseActivity
import com.traday.longholder.presentation.base.BottomNavigationViewProvider
import com.traday.longholder.presentation.base.StartDestinationHandler
import com.traday.longholder.presentation.base.TabBarHandler
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main), TabBarHandler,
    BottomNavigationViewProvider, StartDestinationHandler {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val viewModel: MainViewModel by viewModels()

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment }

    private val navController by lazy { navHostFragment.navController }

    private val rootGraph by lazy { navController.navInflater.inflate(R.navigation.nav_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initBottomTabs()
        initViewModel()
    }

    private fun initBottomTabs() {
        binding.bnvMain.setupWithNavController(navController)
    }

    private fun initViewModel() {
        with(viewModel) {
            userStatus.collectLatestWhenStarted(this@MainActivity) {
                when (it) {
                    is Resource.Error -> handleUserStatus(UserStatus.NOT_AUTHORIZED)
                    is Resource.Success -> handleUserStatus(it.data)
                }
            }
            selectedLanguage.collectLatestWhenStarted(this@MainActivity) {
                if (it is Resource.Success && it.data.shortName != Locale.getDefault().language) {
                    recreate()
                }
            }
        }
    }

    private fun handleUserStatus(userStatus: UserStatus) {
        val destinationId = when (userStatus) {
            UserStatus.NOT_AUTHORIZED -> R.id.nav_start
            UserStatus.NOT_VERIFIED -> TODO()
            UserStatus.AUTHORIZED -> R.id.nav_wallet
            UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING -> {
                setStartDestinationForNestedGraph(
                    graphId = R.id.nav_start,
                    destinationId = R.id.onboardingFragment
                )
                R.id.nav_start
            }
        }
        setStartDestination(destinationId)
    }

    override fun setStartDestination(
        @IdRes destinationId: Int,
        args: Bundle?
    ) {
        rootGraph.setStartDestination(destinationId)
        args?.let {
            navController.setGraph(rootGraph, it)
        } ?: run {
            navController.graph = rootGraph
        }
    }

    override fun setStartDestinationForNestedGraph(@IdRes graphId: Int, @IdRes destinationId: Int) {
        val graph = rootGraph.findNode(graphId) as NavGraph
        graph.setStartDestination(destinationId)
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
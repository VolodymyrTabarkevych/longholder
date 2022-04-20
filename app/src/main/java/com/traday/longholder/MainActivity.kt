package com.traday.longholder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.databinding.ActivityMainBinding
import com.traday.longholder.extensions.gone
import com.traday.longholder.extensions.show
import com.traday.longholder.presentation.base.TabBarHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), TabBarHandler {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
    private val rootGraph by lazy { navController.navInflater.inflate(R.navigation.nav_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Longholder)
        super.onCreate(savedInstanceState)
    }

    override fun showTabs() {
        binding.bnvMain.show()
    }

    override fun hideTabs() {
        binding.bnvMain.gone()
    }
}
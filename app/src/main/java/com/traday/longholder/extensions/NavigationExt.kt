package com.traday.longholder.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

@Throws(IllegalStateException::class)
fun AppCompatActivity.findNavControllerSafe(@IdRes id: Int): NavController {
    if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        return Navigation.findNavController(this, id)
    }
    val fragment: Fragment? = this.supportFragmentManager.findFragmentById(id)
    check(fragment is NavHostFragment) {
        ("Activity doesn't have a NavHostFragment or id doesn't correspond to NavHostFragment")
    }
    return fragment.navController
}

fun NavController.navigateSafe(@IdRes destination: Int) {
    val action = currentDestination?.getAction(destination) ?: graph.getAction(destination)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(destination)
    }
}

fun NavController.navigateSafe(direction: NavDirections) {
    val action =
        currentDestination?.getAction(direction.actionId) ?: graph.getAction(direction.actionId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(direction)
    }
}

fun NavController.navigateSafe(@IdRes destination: Int, navOptions: NavOptions) {
    val action = currentDestination?.getAction(destination) ?: graph.getAction(destination)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(destination, null, navOptions)
    }
}
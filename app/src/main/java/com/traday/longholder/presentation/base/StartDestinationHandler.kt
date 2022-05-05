package com.traday.longholder.presentation.base

import android.os.Bundle
import androidx.annotation.IdRes

interface StartDestinationHandler {

    fun setStartDestination(@IdRes destinationId: Int, args: Bundle? = null)

    fun setStartDestinationForNestedGraph(@IdRes graphId: Int, @IdRes destinationId: Int)
}
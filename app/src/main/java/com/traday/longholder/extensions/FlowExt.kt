package com.traday.longholder.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

inline fun <reified T> Flow<T>.collectLatestWhenStarted(
    lifecycleOwner: LifecycleOwner,
    noinline action: suspend (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launchWhenStarted {
    collectLatest(action)
}
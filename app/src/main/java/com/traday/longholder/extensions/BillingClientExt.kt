package com.traday.longholder.extensions

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <T> BillingClient.safeConnection(
    onBillingConnected: suspend () -> T
): T {
    if (!isReady) awaitConnection()
    return onBillingConnected.invoke()
}

suspend fun BillingClient.awaitConnection(): Boolean = suspendCoroutine {
    this.startConnection(object : BillingClientStateListener {
        override fun onBillingSetupFinished(billingResult: BillingResult) {
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                it.resume(true)
            }
        }

        override fun onBillingServiceDisconnected() {
            it.resume(false)
        }
    })
}
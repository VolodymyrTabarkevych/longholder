package com.traday.longholder.extensions

import android.content.Context
import com.traday.longholder.App

val Context.app: App
    get() = applicationContext as App
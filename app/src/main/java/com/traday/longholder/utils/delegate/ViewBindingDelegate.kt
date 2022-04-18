package com.traday.longholder.utils.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

class ViewBindingDelegate<B : ViewBinding>(
    val fragment: Fragment,
    private val binder: (View) -> B
) : DefaultLifecycleObserver {

    private var binding: B? = null

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        if (binding == null) fragment.viewLifecycleOwner.lifecycle.addObserver(this)
        return binding ?: binder(thisRef.requireView()).also { binding = it }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        binding = null
        fragment.viewLifecycleOwner.lifecycle.removeObserver(this)
    }
}

fun <B : ViewBinding> Fragment.viewBindings(binder: (View) -> B): ViewBindingDelegate<B> {
    return ViewBindingDelegate(this, binder)
}

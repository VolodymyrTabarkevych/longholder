package com.traday.longholder.presentation.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.traday.longholder.extensions.hideKeyboard

abstract class BaseFragment<out B : ViewBinding>(
    @LayoutRes private val layoutResId: Int,
    private val tabBarMode: TabBarMode
) : BaseInjectFragment() {

    protected abstract val binding: B

    protected val navController by lazy { findNavController() }

    protected val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    private var tabBarHandler: TabBarHandler? = null

    private var _view: View? = null

    private var isFirstCreate = true

    protected abstract fun initView(inflatedView: View, args: Bundle?)

    protected abstract fun initViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        tabBarHandler = activity as? TabBarHandler
            ?: throw IllegalStateException("Activity ($activity) must implement ${TabBarHandler::class.java.name}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_view == null)
            _view = LayoutInflater.from(context).inflate(layoutResId, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (tabBarMode == TabBarMode.VISIBLE)
            tabBarHandler?.showTabs()
        else
            tabBarHandler?.hideTabs()

        if (isFirstCreate) {
            isFirstCreate = false
            initView(view, savedInstanceState)
        }
        initViewModel()

        view.post {
            hideKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainHandler.removeCallbacksAndMessages(null)
        (_view?.parent as? ViewGroup)?.removeView(_view)
    }
}
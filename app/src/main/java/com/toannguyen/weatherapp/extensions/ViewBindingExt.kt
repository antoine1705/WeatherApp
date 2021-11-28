package com.toannguyen.weatherapp.extensions

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> AppCompatActivity.viewBinding(function: View.() -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        val contentView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        function(contentView)
    }

fun <T : ViewBinding> Fragment.viewBinding(
    function: View.() -> T,
    onBeforeDestroy: ((T?) -> Unit)? = null
): Lazy<T> = object :
    Lazy<T>,
    LifecycleEventObserver {
    private var mValue: T? = null

    override val value: T
        get() {
            if (mValue == null) mValue = function(requireView())
            observe()
            return mValue!!
        }

    fun observe() {
        viewLifecycleOwnerLiveData.observe(this@viewBinding, { it.lifecycle.addObserver(this) })
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            onBeforeDestroy?.invoke(mValue)
            source.lifecycle.removeObserver(this)
            mValue = null
        }
    }

    override fun isInitialized(): Boolean {
        return mValue != null
    }
}

fun <T : ViewBinding> Dialog.viewBinding(
    function: (context: LayoutInflater) -> T,
    isAttach: Boolean = true
): T {
    val binding = function(LayoutInflater.from(context))
    if (isAttach) setContentView(binding.root)
    return binding
}

operator fun <T : ViewBinding> ViewGroup.get(
    inflater: (LayoutInflater, ViewGroup, attach: Boolean) -> T,
    attach: Boolean = false
): T {
    return inflater(LayoutInflater.from(context), this, attach)
}

fun ViewBinding.getString(@StringRes resId: Int): String {
    return root.context.getString(resId)
}

fun View.getString(@StringRes resId: Int): String {
    return this.context.getString(resId)
}

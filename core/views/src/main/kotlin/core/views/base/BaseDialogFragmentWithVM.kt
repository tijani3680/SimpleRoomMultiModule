package core.views.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import core.views.util.OnBackPressListener
import core.views.util.instancestate.InstanceStateHandler
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseDialogFragmentWithVM
<B : ViewBinding, S : BaseState<*, *>, E : BaseEffect, VM : BaseVM<*, *, E, S>>(
    private val clazz: Class<VM>
) : DialogFragment(), HasAndroidInjector, OnBackPressListener, InstanceStateHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[clazz]
    }

    private var _binding: B? = null
    protected var binding: B
        get() = _binding ?: throw IllegalAccessException("")
        set(value) {
            _binding = value
        }

    private lateinit var listenToState: Job
    private lateinit var listenToEffect: Job

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreBundle(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initialView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveBundle(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean {
        val childFragment = (childFragmentManager.primaryNavigationFragment as? NavHostFragment)
            ?.childFragmentManager?.primaryNavigationFragment
            ?: childFragmentManager.primaryNavigationFragment
        return (childFragment as? OnBackPressListener)?.onBackPressed() ?: false
    }

    protected open fun initialView(savedInstanceState: Bundle?) {
        if (::listenToState.isInitialized) listenToState.cancel()
        if (::listenToEffect.isInitialized) listenToEffect.cancel()
        listenToState = lifecycleScope.launchWhenStarted {
            viewModel.state.onEach { renderState(it) }.collect()
        }
        listenToEffect = lifecycleScope.launchWhenStarted {
            viewModel.effect.onEach { renderEffect(it) }.collect()
        }
    }

    abstract fun renderEffect(effect: E)
    abstract fun renderState(state: S)
}

package core.views.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import core.views.util.OnBackPressListener
import core.views.util.instancestate.InstanceStateHandler
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseActivityWithVM
<B : ViewBinding, S : BaseState<*, *>, E : BaseEffect, VM : BaseVM<*, *, E, S>>(
    private val clazz: Class<VM>
) : AppCompatActivity(), HasAndroidInjector, InstanceStateHandler {

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
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreBundle(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveBundle(outState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun isAllPermissionsGranted(permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (permissions.isEmpty()) return true
        if (grantResults.isEmpty() || grantResults.size != permissions.size) return false
        grantResults.forEach { if (it != PackageManager.PERMISSION_GRANTED) return false }
        return true
    }

    fun isAllPermissionsGranted(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(applicationContext, it)
                != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }

    override fun onBackPressed() {
        val fragment = (supportFragmentManager.primaryNavigationFragment as? NavHostFragment)
            ?.childFragmentManager?.primaryNavigationFragment
            ?: supportFragmentManager.primaryNavigationFragment
        if ((fragment as? OnBackPressListener)?.onBackPressed() != true)
            super.onBackPressed()
    }

    protected fun hideKeyboard(view: View) {
        view.clearFocus()
        val input: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view.windowToken, 0)
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

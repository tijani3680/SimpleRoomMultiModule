package core.views.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseVM
<A : BaseAction, M : BaseMutation, E : BaseEffect, S : BaseState<A, M>>(
    private val clazz: Class<S>
) : ViewModel() {

    private val mutableAction = MutableSharedFlow<A>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 64
    )

    private val mutableEffect = MutableSharedFlow<E>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 64
    )
    val effect = mutableEffect.asSharedFlow()

    private val mutableState = mutableState()
    val state: StateFlow<S> = mutableState.asStateFlow()
    private val mutex = Mutex()

    init {
        mutableAction.asSharedFlow().onEach { a ->
            if (state.value.isActionRegistered(a)) handle(a).onEach { mutation ->
                mutex.withLock {
                    clazz.cast(state.value.reduce(mutation))?.let { mutableState.value = it }
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    fun emitAction(action: A) = mutableAction.tryEmit(action)
    protected fun emitEffect(effect: E) = mutableEffect.tryEmit(effect)

    private fun mutableState() = MutableStateFlow(initialState())
    protected abstract fun initialState(): S
    protected abstract fun handle(action: A): Flow<M>
}

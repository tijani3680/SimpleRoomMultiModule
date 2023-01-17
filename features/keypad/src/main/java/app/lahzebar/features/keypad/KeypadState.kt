package app.lahzebar.features.keypad

import core.views.base.BaseState

sealed class KeypadState : BaseState<KeypadAction, KeypadMutation> {
    data class Init(val lastUpdateContact: Long? = null) : KeypadState() {
        override fun reduce(mutation: KeypadMutation): BaseState<KeypadAction, KeypadMutation> {
            return when (mutation) {
                is KeypadMutation.ReadContact -> this.copy(lastUpdateContact = mutation.lastContactUpdate)
            }
        }
    }
}

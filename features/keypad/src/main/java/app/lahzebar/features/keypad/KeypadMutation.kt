package app.lahzebar.features.keypad

import core.views.base.BaseMutation

sealed class KeypadMutation : BaseMutation {
    data class ReadContact(val lastContactUpdate: Long) : KeypadMutation()
}

package app.lahzebar.features.keypad

import core.views.base.BaseEffect

sealed class KeypadEffect : BaseEffect {
    data class ReadContact(val lastContactUpdate: Long) : KeypadEffect()
    object ReadHistory : KeypadEffect()
}

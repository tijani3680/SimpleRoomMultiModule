package app.lahzebar.features.edit

import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseEffect

sealed class EditEffect : BaseEffect {
    object Navigate : EditEffect()
    data class Error(val error: BaseException) : EditEffect()
}

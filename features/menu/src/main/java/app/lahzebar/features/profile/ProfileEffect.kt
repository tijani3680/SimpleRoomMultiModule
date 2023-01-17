package app.lahzebar.features.profile

import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseEffect

sealed class ProfileEffect : BaseEffect {
    data class Error(val error: BaseException) : ProfileEffect()
}

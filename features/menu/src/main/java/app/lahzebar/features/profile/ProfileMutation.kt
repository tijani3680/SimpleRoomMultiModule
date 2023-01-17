package app.lahzebar.features.profile

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseMutation

sealed class ProfileMutation : BaseMutation {
    data class DataLoaded(val fndMe: FndMe) : ProfileMutation()
}

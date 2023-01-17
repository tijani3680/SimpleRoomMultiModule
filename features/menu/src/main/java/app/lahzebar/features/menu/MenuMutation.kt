package app.lahzebar.features.menu

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseMutation

sealed class MenuMutation : BaseMutation {
    data class LoadData(val fndMe: FndMe) : MenuMutation()
}

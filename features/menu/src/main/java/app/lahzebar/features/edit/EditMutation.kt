package app.lahzebar.features.edit

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseMutation

sealed class EditMutation : BaseMutation {
    data class Data(val fndMe: FndMe) : EditMutation()
}

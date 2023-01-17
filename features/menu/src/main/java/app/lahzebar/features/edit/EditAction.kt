package app.lahzebar.features.edit

import app.lahzebar.domain.model.Bubble
import core.views.base.BaseAction

sealed class EditAction : BaseAction {
    object LoadData : EditAction()
    data class UpdateProfile(val bubble: Bubble) : EditAction()
}

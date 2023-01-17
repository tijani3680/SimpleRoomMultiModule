package app.lahzebar.features.profile

import core.views.base.BaseAction

sealed class ProfileAction : BaseAction {
    object LoadData : ProfileAction()
    data class FndOne(val palId: String) : ProfileAction()
}

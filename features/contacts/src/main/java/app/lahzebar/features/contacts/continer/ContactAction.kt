package app.lahzebar.features.contacts.continer

import core.views.base.BaseAction

sealed class ContactAction : BaseAction {
    object Initial : ContactAction()
}

package app.lahzebar.features.contacts.frequent

import core.views.base.BaseAction

sealed class FrequentAction : BaseAction {
    object Initial : FrequentAction()
}

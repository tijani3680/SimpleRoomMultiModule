package app.lahzebar.features.contacts.favorite

import core.views.base.BaseAction

sealed class FavoriteAction : BaseAction {
    object Initial : FavoriteAction()
}

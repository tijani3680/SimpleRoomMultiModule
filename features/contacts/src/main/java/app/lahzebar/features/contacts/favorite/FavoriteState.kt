package app.lahzebar.features.contacts.favorite

import core.views.base.BaseState

sealed class FavoriteState : BaseState<FavoriteAction, FavoriteMutation> {
    object Initial : FavoriteState()
}

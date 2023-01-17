package app.lahzebar.features.contacts.favorite

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class FavoriteViewModel @Inject constructor() :
    BaseVM<FavoriteAction, FavoriteMutation, FavoriteEffect, FavoriteState>(FavoriteState::class.java) {

    override fun initialState(): FavoriteState = FavoriteState.Initial

    override fun handle(action: FavoriteAction): Flow<FavoriteMutation> = emptyFlow()
}

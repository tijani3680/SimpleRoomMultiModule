package app.lahzebar.features.menu

import app.lahzebar.domain.api.StoreDataSource
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource
) : BaseVM<MenuAction, MenuMutation, MenuEffect, MenuState>(MenuState::class.java) {
    override fun initialState(): MenuState = MenuState.Init()

    override fun handle(action: MenuAction): Flow<MenuMutation> = flow {
        when (action) {
            is MenuAction.LoadData -> {
                storeDataSource.getFnd().first()?.let { fndMe ->
                    emit(MenuMutation.LoadData(fndMe))
                } ?: storeDataSource.saveFnd(MenuFragment.SAMPLE_FND_ME)
            }
        }
    }
}

package app.lahzebar.features.menu

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseState

sealed class MenuState : BaseState<MenuAction, MenuMutation> {
    data class Init(
        val fndMe: FndMe? = null
    ) : MenuState() {
        override fun reduce(mutation: MenuMutation): BaseState<MenuAction, MenuMutation> {
            return when (mutation) {
                is MenuMutation.LoadData -> this.copy(fndMe = mutation.fndMe)
            }
        }
    }
}

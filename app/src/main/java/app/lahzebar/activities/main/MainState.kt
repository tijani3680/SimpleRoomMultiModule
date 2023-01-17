package app.lahzebar.activities.main

import core.views.base.BaseState

sealed class MainState : BaseState<MainAction, MainMutation> {
    object Init : MainState()
}

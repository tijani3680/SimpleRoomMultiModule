package app.lahzebar.features.contacts.frequent

import core.views.base.BaseState

sealed class FrequentState : BaseState<FrequentAction, FrequentMutation> {
    object Initial : FrequentState()
}

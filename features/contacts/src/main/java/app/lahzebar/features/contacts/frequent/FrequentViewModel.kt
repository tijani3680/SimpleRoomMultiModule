package app.lahzebar.features.contacts.frequent

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class FrequentViewModel @Inject constructor() :
    BaseVM<FrequentAction, FrequentMutation, FrequentEffect, FrequentState>(FrequentState::class.java) {

    override fun initialState(): FrequentState = FrequentState.Initial

    override fun handle(action: FrequentAction): Flow<FrequentMutation> = emptyFlow()
}

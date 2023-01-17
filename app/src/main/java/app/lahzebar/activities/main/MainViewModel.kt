package app.lahzebar.activities.main

import app.lahzebar.SampleData
import app.lahzebar.domain.intractor.GenerateDataInteractor
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val generateDataInteractor: GenerateDataInteractor
) :
    BaseVM<MainAction, MainMutation, MainEffect, MainState>(MainState::class.java) {

    override fun initialState(): MainState = MainState.Init

    override fun handle(action: MainAction): Flow<MainMutation> = channelFlow {
        when (action) {
            MainAction.insertPrimaryData -> generateDataInteractor(SampleData.generatePrimaryData())
        }
    }
}

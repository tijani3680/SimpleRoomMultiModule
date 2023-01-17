package app.lahzebar.features.settings

import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.room.UserConfig
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val storeDataSource: StoreDataSource) :
    BaseVM<SettingsAction, SettingsMutation, SettingsEffect, SettingsState>(SettingsState::class.java) {

    override fun initialState(): SettingsState = SettingsState.Initial
    override fun handle(action: SettingsAction): Flow<SettingsMutation> = channelFlow {
        when (action) {
            is SettingsAction.Initial -> {
                val config = storeDataSource.getUserConfig().first()
                config?.theme?.let { send(SettingsMutation.Config(it)) }
            }
            is SettingsAction.SaveConfig -> {
                storeDataSource.saveUserConfig(UserConfig(theme = action.theme))
                send(SettingsMutation.Config(action.theme))
            }
        }
    }
}

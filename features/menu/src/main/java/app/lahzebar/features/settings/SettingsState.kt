package app.lahzebar.features.settings

import app.lahzebar.domain.model.room.UserConfig
import core.views.base.BaseState

sealed class SettingsState : BaseState<SettingsAction, SettingsMutation> {
    object Initial : SettingsState() {
        override fun reduce(mutation: SettingsMutation): BaseState<SettingsAction, SettingsMutation> {
            return when (mutation) {
                is SettingsMutation.Config -> LoadTheme(mutation.data)
            }
        }
    }

    data class LoadTheme(
        val theme: UserConfig.Theme
    ) : SettingsState() {
        override fun reduce(mutation: SettingsMutation): BaseState<SettingsAction, SettingsMutation> {
            return when (mutation) {
                is SettingsMutation.Config -> this.copy(theme = mutation.data)
            }
        }
    }
}

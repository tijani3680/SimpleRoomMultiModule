package app.lahzebar.features.settings

import app.lahzebar.domain.model.room.UserConfig
import core.views.base.BaseAction

sealed class SettingsAction : BaseAction {
    object Initial : SettingsAction()
    data class SaveConfig(
        val theme: UserConfig.Theme
    ) : SettingsAction()
}

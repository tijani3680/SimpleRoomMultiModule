package app.lahzebar.features.settings

import app.lahzebar.domain.model.room.UserConfig
import core.views.base.BaseMutation

sealed class SettingsMutation : BaseMutation {

    data class Config(val data: UserConfig.Theme) : SettingsMutation()
}

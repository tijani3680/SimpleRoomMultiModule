package app.lahzebar.features.profile

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseState

sealed class ProfileState : BaseState<ProfileAction, ProfileMutation> {
    object Init : ProfileState() {
        override fun reduce(mutation: ProfileMutation): BaseState<ProfileAction, ProfileMutation> {
            return when (mutation) {
                is ProfileMutation.DataLoaded -> DataLoaded(mutation.fndMe)
            }
        }
    }

    data class DataLoaded(val data: FndMe) : ProfileState()
}

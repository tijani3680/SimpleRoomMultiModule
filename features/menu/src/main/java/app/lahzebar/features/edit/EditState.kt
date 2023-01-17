package app.lahzebar.features.edit

import app.lahzebar.domain.model.FndMe
import core.views.base.BaseState

sealed class EditState : BaseState<EditAction, EditMutation> {
    data class Init(
        val data: FndMe? = null
    ) : EditState() {
        override fun reduce(mutation: EditMutation): BaseState<EditAction, EditMutation> {
            return when (mutation) {
                is EditMutation.Data -> this.copy(data = mutation.fndMe)
            }
        }
    }
}

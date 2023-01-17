package app.lahzebar.features.edit

import app.lahzebar.domain.api.RemoteDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val remoteDataSource: RemoteDataSource
) : BaseVM<EditAction, EditMutation, EditEffect, EditState>(
    EditState::class.java
) {
    override fun initialState(): EditState = EditState.Init()

    override fun handle(action: EditAction): Flow<EditMutation> = flow {
        when (action) {
            EditAction.LoadData -> {
                storeDataSource.getFnd().first()?.let { fndMenu ->
                    emit(EditMutation.Data(fndMenu))
                }
            }
            is EditAction.UpdateProfile -> {
                try {
                    storeDataSource.getUserConfig().first()?.token?.let { token ->
                        when (remoteDataSource.updProfile(token, action.bubble).message) {
                            EditFragment.BUBBLE_UPDATED -> emitEffect(EditEffect.Navigate)
                            else -> {}
                        }
                    }
                } catch (ex: BaseException) {
                    emitEffect(EditEffect.Error(ex))
                }
            }
        }
    }
}

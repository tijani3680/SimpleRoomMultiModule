package app.lahzebar.features.profile

import app.lahzebar.domain.api.RemoteDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val remoteDataSource: RemoteDataSource
) : BaseVM<ProfileAction, ProfileMutation, ProfileEffect, ProfileState>(ProfileState::class.java) {
    override fun initialState(): ProfileState = ProfileState.Init

    override fun handle(action: ProfileAction): Flow<ProfileMutation> = flow {
        when (action) {
            ProfileAction.LoadData -> {
                storeDataSource.getFnd().first()?.let {
                    emit(ProfileMutation.DataLoaded(it))
                }
            }
            is ProfileAction.FndOne -> {
                storeDataSource.getUserConfig().first()?.token?.let {
                    try {
                        val data = remoteDataSource.fndOne(it, action.palId)
                        emit(ProfileMutation.DataLoaded(data))
                    } catch (ex: BaseException) {
                        emitEffect(ProfileEffect.Error(ex))
                    }
                }
            }
        }
    }
}

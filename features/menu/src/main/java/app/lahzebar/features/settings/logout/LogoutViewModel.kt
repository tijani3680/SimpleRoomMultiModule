package app.lahzebar.features.settings.logout

import app.lahzebar.domain.api.RemoteDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val remoteDataSource: RemoteDataSource
) :
    BaseVM<LogoutAction, LogoutMutation, LogoutEffect, LogoutState>(LogoutState::class.java) {
    override fun initialState(): LogoutState = LogoutState.Initial

    override fun handle(action: LogoutAction): Flow<LogoutMutation> = flow {

        when (action) {
            is LogoutAction.Logout -> {

                val token = storeDataSource.getUserConfig().first()?.token
                try {

                    token?.let {
                        val request = remoteDataSource.logout(it)
                        if (request.message == "Logout Successful") {
                            storeDataSource.clearDataStore()
                            emitEffect(LogoutEffect.Logout)
                        }
                    }
                } catch (ex: BaseException) {

                    emitEffect(LogoutEffect.Error(ex))
                }
            }
        }
    }
}

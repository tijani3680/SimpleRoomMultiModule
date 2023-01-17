package app.lahzebar.features.login

import app.lahzebar.domain.api.AuthDataSource
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : BaseVM<LoginAction, LoginMutation, LoginEffect, LoginState>(LoginState::class.java) {
    override fun initialState(): LoginState = LoginState.Init

    override fun handle(action: LoginAction): Flow<LoginMutation> = flow {
        when (action) {
            is LoginAction.PrimaryPhoneNumber -> {
                try {
                    val phoneNumber = action.number.replace(" ", "").replace("-", "")
                    val primary = authDataSource.primaryPhoneResponse(phoneNumber)
                    emitEffect(
                        LoginEffect.NavigateToMode(
                            phoneNumber = action.number, hasPass = primary.hasPass
                        )
                    )
                } catch (ex: BaseException) {
                    emitEffect(LoginEffect.ShowError(ex.message.toString()))
                }
            }
        }
    }
}

package app.lahzebar.features.pass

import android.os.Build
import app.lahzebar.domain.api.AuthDataSource
import app.lahzebar.domain.model.LoginBody
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PasswordLoginViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : BaseVM<PasswordLoginAction, PasswordLoginMutation, PasswordLoginEffect, PasswordLoginState>(
    PasswordLoginState::class.java
) {

    override fun initialState(): PasswordLoginState = PasswordLoginState.Init

    override fun handle(action: PasswordLoginAction): Flow<PasswordLoginMutation> = flow {
        when (action) {
            is PasswordLoginAction.Login -> {
                login(action)
            }
        }
    }

    private suspend fun login(action: PasswordLoginAction.Login) {
        try {
            val sdkVersion = Build.VERSION.SDK_INT
            val loginBody = LoginBody(
                action.phoneNumber.replace(" ", ""), action.deviceTitle,
                PasswordLoginFragment.FIREBASE_TOKEN, PasswordLoginFragment.DEVICE_OS,
                sdkVersion.toString(), action.version, action.password, ""
            )
            authDataSource.login(loginBody)
            emitEffect(PasswordLoginEffect.Navigate)
        } catch (ex: BaseException) {
            emitEffect(PasswordLoginEffect.ShowError(ex.message.toString()))
        }
    }
}

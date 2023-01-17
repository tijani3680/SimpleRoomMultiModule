package app.lahzebar.features.mode

import android.os.Build
import app.lahzebar.domain.intractor.LoginInteractor
import app.lahzebar.domain.model.LoginBody
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModeViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : BaseVM<ModeAction, ModeMutation, ModeEffect, ModeState>(ModeState::class.java) {
    override fun initialState(): ModeState = ModeState.Init

    override fun handle(action: ModeAction): Flow<ModeMutation> = flow {
        when (action) {
            is ModeAction.Login -> {
                login(action)
            }
        }
    }

    private suspend fun login(action: ModeAction.Login) {
        try {
            val sdkVersion = Build.VERSION.SDK_INT
            val loginBody = LoginBody(
                action.phoneNumber.replace(" ", "").replace("-", ""),
                action.deviceTitle,
                ModeFragment.FIREBASE_TOKEN,
                ModeFragment.DEVICE_OS,
                sdkVersion.toString(),
                action.version,
                null,
                action.type
            )
            loginInteractor(loginBody).let {
                when (action.type) {
                    ModeFragment.SMS_TYPE -> emitEffect(ModeEffect.NavigateToVerify(false))
                    ModeFragment.CALL_TYPE -> emitEffect(ModeEffect.NavigateToVerify(true))
                    else -> {}
                }
            }
        } catch (ex: BaseException) {
            emitEffect(ModeEffect.ShowError(ex.message.toString()))
        }
    }
}

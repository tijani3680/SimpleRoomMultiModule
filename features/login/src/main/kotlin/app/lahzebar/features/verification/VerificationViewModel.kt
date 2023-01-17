package app.lahzebar.features.verification

import app.lahzebar.domain.api.RemoteDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerificationViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val storeDataSource: StoreDataSource
) : BaseVM<VerificationAction, VerificationMutation, VerificationEffect, VerificationState>(
    VerificationState::class.java
) {

    override fun initialState(): VerificationState = VerificationState.Init

    override fun handle(action: VerificationAction): Flow<VerificationMutation> = flow {
        when (action) {
            is VerificationAction.OtpCode -> {
                verification(action)
            }
        }
    }

    private suspend fun verification(action: VerificationAction.OtpCode) {
        try {
            val fields: MutableMap<String, String> = mutableMapOf()
            fields[VerificationFragment.CODE] = action.otpCode
            val token = storeDataSource.getUserConfig().first()?.token
            token?.let {
                val req = remoteDataSource.veryCode(it, fields)
                emitEffect(VerificationEffect.React(true))
                delay(500)
                when (req.status) {
                    VerificationFragment.STATUS_ACTIVE ->
                        emitEffect(VerificationEffect.NavigateToKeypad)
                    VerificationFragment.STATUS_REG ->
                        emitEffect(VerificationEffect.NavigateToReg)
                    else -> {}
                }
            }
        } catch (ex: BaseException) {
            emitEffect(VerificationEffect.React(false, ex.message))
        }
    }

    // todo smsCatcher
}

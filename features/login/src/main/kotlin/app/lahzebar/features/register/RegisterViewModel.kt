package app.lahzebar.features.register

import app.lahzebar.domain.api.RemoteDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.model.Bubble
import app.lahzebar.domain.model.exception.BaseException
import app.lahzebar.domain.model.room.UserConfig
import core.utils.safeLet
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val remoteDataSource: RemoteDataSource
) : BaseVM<RegisterAction, RegisterMutation, RegisterEffect, RegisterState>(RegisterState::class.java) {

    override fun initialState(): RegisterState = RegisterState.Init

    override fun handle(action: RegisterAction): Flow<RegisterMutation> = flow {
        when (action) {
            is RegisterAction.RegBusiness -> {
                register(action)
            }
            is RegisterAction.RegPerson -> {
                register(action)
            }
        }
    }

    private suspend fun register(action: RegisterAction) {
        try {
            val token = storeDataSource.getUserConfig().first()?.token
            var bubble: Bubble? = null
            when (action) {
                is RegisterAction.RegBusiness -> {
                    bubble = Bubble(
                        accessLvl = "Public", cityName = "Tehran", companyName = action.companyName,
                        displayName = action.companyName, gender = "Male", isBiz = true,
                        nature = false, slogan = action.slogan, status = "Active"
                    )
                }

                is RegisterAction.RegPerson -> {
                    bubble = Bubble(
                        accessLvl = "Public", cityName = "Tehran", dateOfBirth = action.birthDate,
                        firstName = action.firstName, lastName = action.lastName,
                        displayName = action.firstName + " " + action.lastName,
                        gender = "Male", nature = true, status = "Active"
                    )

                    storeDataSource.saveUserConfig(userConfig = UserConfig(birthDate = action.birthDate))
                }
            }

            safeLet(token, bubble) { t, b ->
                remoteDataSource.register(t, b)
                emitEffect(RegisterEffect.Navigate)
            }
        } catch (ex: BaseException) {
            emitEffect(RegisterEffect.ShowError(ex.message.toString()))
        }
    }
}

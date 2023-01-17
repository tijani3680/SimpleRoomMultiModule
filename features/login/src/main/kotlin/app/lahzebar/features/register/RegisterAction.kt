package app.lahzebar.features.register

import core.views.base.BaseAction

sealed class RegisterAction : BaseAction {
    data class RegPerson(
        val firstName: String,
        val lastName: String,
        val birthDate: String
    ) : RegisterAction()
    data class RegBusiness(
        val companyName: String,
        val slogan: String
    ) : RegisterAction()
}

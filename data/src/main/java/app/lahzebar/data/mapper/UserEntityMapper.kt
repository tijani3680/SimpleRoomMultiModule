package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.UserEntity
import app.lahzebar.domain.model.room.User

internal fun UserEntity.toDomain() = User(
    userId = userId,
    name = name
)

internal fun User.toEntity() = UserEntity(
    userId = userId,
    name = name
)

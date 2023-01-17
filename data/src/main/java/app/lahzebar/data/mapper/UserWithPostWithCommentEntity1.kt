package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.UserWithPostWithCommentEntity1
import app.lahzebar.domain.model.room.UserWithPostWithComment1

internal fun UserWithPostWithCommentEntity1.toDomain() = UserWithPostWithComment1(
    user = userEntity.toDomain(),
    posWithComment = posWithComment.map { it.toDomain() }
)

package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.PostWithCommentEntity1
import app.lahzebar.domain.model.room.PostWithComment1

internal fun PostWithCommentEntity1.toDomain() = PostWithComment1(
    post =postEntity.toDomain(),
    comments= comments.map { it.toDomain() },
)


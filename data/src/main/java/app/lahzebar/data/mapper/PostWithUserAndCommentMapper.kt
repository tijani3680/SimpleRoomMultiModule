package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.PostWithUserAndCommentEntity
import app.lahzebar.domain.model.room.PostWithUserAndComment

internal fun PostWithUserAndCommentEntity.toDomain() = PostWithUserAndComment(
    post = post.toDomain(),
    comments = comments.map { it.toDomain() },
    user = user.toDomain()
)

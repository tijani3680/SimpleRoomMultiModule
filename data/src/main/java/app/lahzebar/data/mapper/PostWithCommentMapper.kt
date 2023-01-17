package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.PostWithCommentEntity
import app.lahzebar.data.local.entitys.PostWithUserAndCommentEntity
import app.lahzebar.domain.model.room.PostWithComment
import app.lahzebar.domain.model.room.PostWithUserAndComment

internal fun PostWithCommentEntity.toDomain() = PostWithComment(
    post = post.toDomain(),
    comments = comments.map { it.toDomain() },
)

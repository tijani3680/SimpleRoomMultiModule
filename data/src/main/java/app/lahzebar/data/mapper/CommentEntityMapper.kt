package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.CommentEntity
import app.lahzebar.domain.model.room.Comment

internal fun CommentEntity.toDomain() = Comment(
    postId = postId,
    commentId = commentId,
    title = title
)

internal fun Comment.toEntity() = CommentEntity(
    id = null,
    postId = postId,
    commentId = commentId,
    title = title
)

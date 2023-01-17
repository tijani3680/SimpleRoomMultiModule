package app.lahzebar.data.mapper

import app.lahzebar.data.local.entitys.PostEntity
import app.lahzebar.domain.model.room.Post

internal fun PostEntity.toDomain() = Post(
    userId = userId,
    postId = postId,
    caption = caption,
    image = image,
    like = like,
    yourLiked = yourLiked
)

internal fun Post.toEntity() = PostEntity(
    userId = userId,
    postId = postId,
    caption = caption,
    image = image,
    like = like,
    yourLiked = yourLiked
)

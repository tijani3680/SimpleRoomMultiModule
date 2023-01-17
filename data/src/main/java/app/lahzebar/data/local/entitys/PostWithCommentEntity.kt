package app.lahzebar.data.local.entitys

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithCommentEntity(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "postId",
        entityColumn = "id"
    )
    val comments: List<CommentEntity>,
)

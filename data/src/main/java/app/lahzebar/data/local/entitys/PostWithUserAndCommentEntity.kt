package app.lahzebar.data.local.entitys

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithUserAndCommentEntity(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "postId",
        entityColumn = "postId"
    )
    val comments: List<CommentEntity>,

    @Relation(
        parentColumn = "postId",
        entityColumn = "userId"
    )
    val user: UserEntity

)

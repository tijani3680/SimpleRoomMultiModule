package app.lahzebar.data.local.entitys

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithCommentEntity1(
    @Embedded
    var postEntity: PostEntity,

    @Relation(entity = CommentEntity::class, entityColumn ="postId", parentColumn = "postId" )
    var comments:List<CommentEntity>
)

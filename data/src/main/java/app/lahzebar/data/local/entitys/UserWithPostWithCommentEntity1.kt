package app.lahzebar.data.local.entitys

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPostWithCommentEntity1(
    @Embedded
    var userEntity: UserEntity,
    @Relation(entity = PostEntity::class, entityColumn = "userId",parentColumn = "userId")
    var posWithComment: List<PostWithCommentEntity1>
)

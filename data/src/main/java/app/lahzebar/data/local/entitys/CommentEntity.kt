package app.lahzebar.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val commentId: Int,
    val postId: Int,
    val title: String,
)

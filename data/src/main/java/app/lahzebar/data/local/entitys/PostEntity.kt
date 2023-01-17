package app.lahzebar.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    val postId: Int,
    val userId: Int,
    val caption: String,
    val image: String,
    val like: Int,
    val yourLiked: Boolean = false
)

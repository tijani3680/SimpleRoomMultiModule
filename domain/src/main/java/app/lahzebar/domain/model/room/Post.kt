package app.lahzebar.domain.model.room

data class Post(
    val postId: Int,
    val userId: Int,
    val caption: String,
    val image: String,
    val like: Int,
    val yourLiked: Boolean = false
)

package app.lahzebar.domain.model.room

data class Comment(
    val commentId: Int,
    val postId: Int,
    val title: String,
)

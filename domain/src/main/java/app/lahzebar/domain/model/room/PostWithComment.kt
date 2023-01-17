package app.lahzebar.domain.model.room
data class PostWithComment(
    val post: Post,

    val comments: List<Comment>,
)

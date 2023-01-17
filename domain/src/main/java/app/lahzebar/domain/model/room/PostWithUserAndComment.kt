package app.lahzebar.domain.model.room
data class PostWithUserAndComment(
    val post: Post,

    val comments: List<Comment>,

    val user: User

)

package app.lahzebar.domain.api

import app.lahzebar.domain.model.room.Comment
import kotlinx.coroutines.flow.Flow

interface CommentDataSource {
    suspend fun insertComments(comments: List<Comment>)
    suspend fun insertComment(comments: Comment)
    fun getComments(): Flow<List<Comment>>
    fun getCommentsById(postId: Int): Flow<List<Comment>>
}

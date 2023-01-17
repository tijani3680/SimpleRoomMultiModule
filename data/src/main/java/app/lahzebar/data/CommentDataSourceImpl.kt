package app.lahzebar.data

import app.lahzebar.data.local.dao.CommentDao
import app.lahzebar.data.mapper.toDomain
import app.lahzebar.data.mapper.toEntity
import app.lahzebar.domain.api.CommentDataSource
import app.lahzebar.domain.model.room.Comment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CommentDataSourceImpl @Inject constructor(
    private val commentDao: CommentDao,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CommentDataSource {
    override suspend fun insertComments(comments: List<Comment>) = withContext(ioDispatcher) {
        commentDao.insertComments(comments.map { it.toEntity() })
    }

    override suspend fun insertComment(comments: Comment) = withContext(ioDispatcher) {
        commentDao.insertComment(comments.toEntity())
    }

    override fun getComments(): Flow<List<Comment>> = commentDao.getComments().map { commentsEntity ->
        commentsEntity.map { it.toDomain() }
    }.flowOn(ioDispatcher)

    override fun getCommentsById(postId: Int): Flow<List<Comment>> = commentDao.getCommentsById(postId).map { commentsEntity ->
        commentsEntity.map { it.toDomain() }
    }.flowOn(ioDispatcher)
}

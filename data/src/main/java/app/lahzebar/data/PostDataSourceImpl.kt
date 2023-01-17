package app.lahzebar.data

import app.lahzebar.data.local.dao.PostDao
import app.lahzebar.data.mapper.toDomain
import app.lahzebar.data.mapper.toEntity
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.model.room.Post
import app.lahzebar.domain.model.room.PostWithUserAndComment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PostDataSourceImpl @Inject constructor(
    private val postDao: PostDao,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PostDataSource {
    override suspend fun insertPosts(posts: List<Post>) = withContext(ioDispatcher) {
        postDao.insertPosts(posts.map { it.toEntity() })
    }

    override fun getPosts(): Flow<List<Post>> = postDao.getPosts().map { postsEntity ->
        postsEntity.map { it.toDomain() }
    }.flowOn(ioDispatcher)

    override suspend fun updateLikePost(likeCount: Int, yourLiked: Boolean, postId: Int) =
        withContext(ioDispatcher) {
            postDao.updateLikePost(likeCount = likeCount, yourLiked = yourLiked, postId = postId)
        }

    override fun getPostWithUserAndCommentById(postId: Int): Flow<PostWithUserAndComment> =
        postDao.getPostWithUserAndCommentById(postId).map { it.toDomain() }.flowOn(ioDispatcher)
}

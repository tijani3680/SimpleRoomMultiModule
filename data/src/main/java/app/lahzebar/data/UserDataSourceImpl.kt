package app.lahzebar.data

import app.lahzebar.data.local.dao.UserDao
import app.lahzebar.data.mapper.toDomain
import app.lahzebar.data.mapper.toEntity
import app.lahzebar.domain.api.UserDataSource
import app.lahzebar.domain.model.room.PostWithComment
import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.User
import app.lahzebar.domain.model.room.UserWithPostWithComment1
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {
    override suspend fun insertUsers(users: List<User>) = withContext(ioDispatcher) {
        userDao.insertUsers(users.map { it.toEntity() })
    }

    override fun getUsers(): Flow<List<User>> = userDao.getUsers().map { usersEntity ->
        usersEntity.map { it.toDomain() }
    }.flowOn(ioDispatcher)

    override fun getPostWithUserAndComment(): Flow<List<PostWithUserAndComment>> =
        userDao.getPosts().map { postWithUserAndCommentEntity ->
            postWithUserAndCommentEntity.map {
                it.toDomain()
            }
        }.flowOn(ioDispatcher)

    override fun getPostWithComment(): Flow<List<PostWithComment>> =
    userDao.getPostsWithComments().map { postWithCommentEntity ->
        postWithCommentEntity.map {
            it.toDomain()
        }
    }.flowOn(ioDispatcher)

    override fun getSuggested(): Flow<List<UserWithPostWithComment1>> =
        userDao.getSuggested().map { postWithCommentEntity ->
            postWithCommentEntity.map {
                it.toDomain()
            }
        }.flowOn(ioDispatcher)

    override fun getPostsByPagination(
        startingLimit: Int,
        limitCount: Int
    ): Flow<List<PostWithUserAndComment>> =
        userDao.getPostsByPagination(startingLimit, limitCount).map { postWithUserAndCommentEntity ->
            postWithUserAndCommentEntity.map {
                it.toDomain()
            }
        }.flowOn(ioDispatcher)

    override fun getSuggestedById(postId: Int): Flow<UserWithPostWithComment1> =
        userDao.getSuggestedById(postId).map { it.toDomain() }
}

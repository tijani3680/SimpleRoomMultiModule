package app.lahzebar.domain.api

import app.lahzebar.domain.model.room.PostWithComment
import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.User
import app.lahzebar.domain.model.room.UserWithPostWithComment1
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun insertUsers(users: List<User>)
    fun getUsers(): Flow<List<User>>
    fun getPostWithUserAndComment(): Flow<List<PostWithUserAndComment>>
    fun getPostWithComment(): Flow<List<PostWithComment>>
    fun getSuggested():Flow<List<UserWithPostWithComment1>>

    fun getPostsByPagination(startingLimit: Int, limitCount: Int): Flow<List<PostWithUserAndComment>>

    fun getSuggestedById(postId: Int):Flow<UserWithPostWithComment1>
}

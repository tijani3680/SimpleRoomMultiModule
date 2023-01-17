package app.lahzebar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.lahzebar.data.local.entitys.PostWithCommentEntity
import app.lahzebar.data.local.entitys.PostWithUserAndCommentEntity
import app.lahzebar.data.local.entitys.UserEntity
import app.lahzebar.data.local.entitys.UserWithPostWithCommentEntity1
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users ORDER BY userId DESC")
    fun getUsers(): Flow<List<UserEntity>>

    @Transaction
    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostWithUserAndCommentEntity>>

    @Transaction
    @Query("SELECT * FROM posts")
    fun getPostsWithComments(): Flow<List<PostWithCommentEntity>>

    @Transaction
    @Query("SELECT * FROM posts ORDER BY postId Limit :startingLimit,:limitCount")
    fun getPostsByPagination(startingLimit: Int, limitCount: Int): Flow<List<PostWithUserAndCommentEntity>>

    @Transaction
    @Query("SELECT * FROM users ")
    fun getSuggested():Flow<List<UserWithPostWithCommentEntity1>>

    @Transaction
    @Query("SELECT * FROM users WHERE userId =:postId ")
    fun getSuggestedById(postId: Int):Flow<UserWithPostWithCommentEntity1>

    @Transaction
    @Query("SELECT * FROM posts WHERE postId =:postId")
    fun getPostWithUserAndCommentById(postId: Int): Flow<PostWithUserAndCommentEntity>
}

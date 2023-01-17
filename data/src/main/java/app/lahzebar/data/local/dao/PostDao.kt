package app.lahzebar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.lahzebar.data.local.entitys.PostEntity
import app.lahzebar.data.local.entitys.PostWithUserAndCommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM posts ORDER BY postId DESC")
    fun getPosts(): Flow<List<PostEntity>>

    @Query("Update posts set `like`=:likeCount,yourLiked =:yourLiked Where postId=:postId ")
    suspend fun updateLikePost(likeCount: Int, yourLiked: Boolean, postId: Int)

    @Transaction
    @Query("SELECT * FROM posts WHERE postId =:postId")
    fun getPostWithUserAndCommentById(postId: Int): Flow<PostWithUserAndCommentEntity>
}

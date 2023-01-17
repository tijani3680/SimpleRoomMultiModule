package app.lahzebar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.lahzebar.data.local.entitys.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)


    @Query("SELECT * FROM comments ORDER BY commentId DESC")
    fun getComments(): Flow<List<CommentEntity>>

    @Query("SELECT * FROM comments WHERE postId=:postId")
    fun getCommentsById(postId: Int): Flow<List<CommentEntity>>
}

package app.lahzebar.domain.api

import app.lahzebar.domain.model.room.Post
import app.lahzebar.domain.model.room.PostWithUserAndComment
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun insertPosts(posts: List<Post>)
    fun getPosts(): Flow<List<Post>>

    suspend fun updateLikePost(likeCount: Int, yourLiked: Boolean, postId: Int)
    fun getPostWithUserAndCommentById(postId: Int): Flow<PostWithUserAndComment>
}
